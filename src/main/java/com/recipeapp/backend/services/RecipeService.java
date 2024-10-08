package com.recipeapp.backend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.recipeapp.backend.entities.FavouriteRecipes;

public interface RecipeService {
  /* GET */
  List<FavouriteRecipes> getSavedRecipesForUser(UUID userId);

  /* POST */
  Boolean addRecipeToFavourites(Long recipeId, UUID userId);
  Boolean removeRecipeFromFavourites(Long recipeId, UUID userId);

  /* RECIPE API */
  ResponseEntity<?> fetchRandomRecipeFromAPI();
  ResponseEntity<?> fetchRecipeByIdFromAPI(Long recipeId);
}
