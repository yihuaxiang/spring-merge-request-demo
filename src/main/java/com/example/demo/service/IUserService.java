package com.example.demo.service;

import com.example.demo.bo.User;

import java.util.Map;

/**
 * @author dongdong.fdd
 * @date 2024/2/12 18:49
 */
public interface IUserService {
  /**
   * 查询用户信息
   */
  Map<String, Object> queryUser(Long id);
}
