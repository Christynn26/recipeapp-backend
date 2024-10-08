package com.recipeapp.backend.services.Implementations;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipeapp.backend.entities.User;
import com.recipeapp.backend.repositories.UserRepository;
import com.recipeapp.backend.services.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User getUserById(UUID id) {
    try {
      User user = userRepository.findById(id).orElseThrow(() -> new Error());
      return user;
    } catch (Exception e) {
      throw new Error();
    }
  }

  @Override
  public Boolean createUser(User user) {
    try {
      user.setCreatedAt(LocalDate.now());
      userRepository.saveAndFlush(user);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Boolean updateUser(UUID id, User user) {
    return true;
  }

  @Override
  public User getUserByEmail(String email) {
    try {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new Error());
      return user;
    } catch (Exception e) {
      throw new Error();
    }
  }

}
