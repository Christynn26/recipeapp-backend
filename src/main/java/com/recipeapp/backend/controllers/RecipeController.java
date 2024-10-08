package com.recipeapp.backend.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.recipeapp.backend.services.RecipeService;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {

  @Autowired
  RecipeService recipeService;

  @GetMapping("/fetch")
  public ResponseEntity<?> fetchRecipes() {
    try {
      return recipeService.fetchRandomRecipeFromAPI();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch recipes");
    }
  }

  @GetMapping("/fetch/{id}")
  public ResponseEntity<?> fetchRecipeById(@PathVariable Long id) {
    try {
      return recipeService.fetchRecipeByIdFromAPI(id);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch recipe.");
    }
  }

  @PostMapping("/{recipeId}/user/{userId}")
  public ResponseEntity<?> addRecipeToUser(@PathVariable Long recipeId, @PathVariable UUID userId) {
    try {
      recipeService.addRecipeToFavourites(recipeId, userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/{recipeId}/user/{userId}/remove")
  public ResponseEntity<?> removeRecipeFromUser(@PathVariable Long recipeId, @PathVariable UUID userId) {
    try {
      recipeService.removeRecipeFromFavourites(recipeId, userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
