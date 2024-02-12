package com.example.demo.controller;

import com.example.demo.bo.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author dongdong.fdd
 * @date 2024/2/12 18:58
 */
@RestController
@RequestMapping("/test")
public class UserController {
  @Autowired
  IUserService userService;

  @GetMapping(value = "/getUserInfo")
  public Map<String, Object> getUserInfo(
          @RequestParam() Long id
  ) {
    return userService.queryUser(id);
  }

}
