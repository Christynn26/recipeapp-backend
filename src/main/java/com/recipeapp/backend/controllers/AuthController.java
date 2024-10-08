package com.recipeapp.backend.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipeapp.backend.entities.User;
import com.recipeapp.backend.services.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {

    if (user.getEmail() == null || user.getPassword() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    try {
      User existingUser = userService.getUserByEmail(user.getEmail());

      System.out.println(existingUser.getPassword());
      System.out.println(user.getPassword());

      if(existingUser.getPassword().equals(user.getPassword())) {
        session.setAttribute("userId", existingUser.getId());
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
      } else {
        Map<String, String> resBody = Map.of("password", "Wrong password");
				return new ResponseEntity<>(resBody, HttpStatus.UNAUTHORIZED);
      }
    } catch (Exception e) {
      Map<String, String> resBody = Map.of("error", e.getMessage());
			return new ResponseEntity<>(resBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
      Boolean success = userService.createUser(user);
      if (success) {
        return new ResponseEntity<User>(HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
