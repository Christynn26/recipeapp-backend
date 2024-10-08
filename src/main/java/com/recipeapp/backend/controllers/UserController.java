package com.recipeapp.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipeapp.backend.entities.FavouriteRecipes;
import com.recipeapp.backend.entities.User;
import com.recipeapp.backend.services.RecipeService;
import com.recipeapp.backend.services.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  RecipeService recipeService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable UUID id) {
    try {
      User user = userService.getUserById(id);
      System.out.println(user);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}/saved-recipes")
  public ResponseEntity<List<FavouriteRecipes>> getSavedRecipesForUser(@PathVariable UUID id) {
    List<FavouriteRecipes> savedRecipes = recipeService.getSavedRecipesForUser(id);
    System.out.println(savedRecipes);
    return new ResponseEntity<>(savedRecipes, HttpStatus.OK);
  }

}
