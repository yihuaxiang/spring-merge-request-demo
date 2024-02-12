package com.example.demo.service;

import com.google.common.base.Joiner;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author dongdong.fdd
 * @date 2024/2/12 18:47
 */
@Service
public class UserService implements IUserService{
  private final Queue<UserQuery> queue = new LinkedBlockingQueue();

  private final RestTemplate restTemplate;

  public UserService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @SneakyThrows
  @Override
  public Map<String, Object> queryUser(Long id) {
    UserQuery userQuery = new UserQuery();
    userQuery.setId(id);

    CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
    userQuery.setCompletableFuture(future);

    queue.add(userQuery);

    return future.get();
  }

  public List<Map<String, Object>> batchQueryUser(List<Long> ids) {
    System.out.println("batchQueryUser, size "+ ids.size());
    return restTemplate.getForEntity("https://playground.z.wiki/test/getUserInfo?ids=" + Joiner.on(",").join(ids), List.class).getBody();
  }

  @PostConstruct
  public void init() {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      System.out.println("run scheduleAtFixedRate");
      int queueSize = this.queue.size();
      if (queueSize == 0) {
        return ;
      }

      List<UserQuery> userQueryList = new ArrayList<>(50);
      final int maxBatchSize = 50;

      for (int i = 0;i<maxBatchSize;i++) {
        if (queue.isEmpty()) {
          break;
        }

        userQueryList.add(queue.poll());
      }

      List<Map<String, Object>> userList = this.batchQueryUser(userQueryList.stream().map(item -> item.getId()).collect(Collectors.toList()));

      userQueryList.forEach(query -> {
        System.out.println("1");
        Integer id = query.getId().intValue();
        System.out.println("2");
        Optional<Map<String, Object>> info = userList.stream().filter(item -> id.equals(((Integer)item.get("id")))).findAny();
        System.out.println("3");
        if (info.isPresent()) {
          System.out.println("4");
          query.getCompletableFuture().complete(info.get());
        } else {
          System.out.println("5");
          query.getCompletableFuture().complete(null);
        }
      });
      System.out.println("6");


    }, 100, 10, TimeUnit.MILLISECONDS);
  }
}
