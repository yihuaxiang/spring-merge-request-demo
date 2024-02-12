package com.example.demo.service;


import com.example.demo.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author dongdong.fdd
 * @date 2024/2/12 18:47
 */
@Service
public class UserService implements IUserService{
  private final RestTemplate restTemplate;

  public UserService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public Map<String, Object> queryUser(Long id) {
    List users = restTemplate.getForEntity("https://playground.z.wiki/test/getUserInfo?ids=" + id, List.class).getBody();
    return Optional.ofNullable(users).map(users1 -> (Map)users1.get(0)).orElse(null);
  }
}
