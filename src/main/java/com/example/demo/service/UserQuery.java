package com.example.demo.service;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author dongdong.fdd
 * @date 2024/2/12 20:00
 */
@Data
public class UserQuery {
  /**
   * 用户ID
   */
  private Long id;

  /**
   * CompletableFuture
   */
  CompletableFuture<Map<String, Object>> completableFuture;
}
