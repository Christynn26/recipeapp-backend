package com.recipeapp.backend.services;

import java.util.UUID;

import com.recipeapp.backend.entities.User;

public interface UserService {
  User getUserById(UUID id);
  User getUserByEmail(String email);
  Boolean createUser(User user);
  Boolean updateUser(UUID id, User user);
}
