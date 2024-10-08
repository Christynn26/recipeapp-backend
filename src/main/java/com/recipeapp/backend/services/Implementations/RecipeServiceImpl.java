package com.recipeapp.backend.services.Implementations;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.recipeapp.backend.Api.RecipeApi;
import com.recipeapp.backend.entities.FavouriteRecipes;
import com.recipeapp.backend.entities.Recipe;
import com.recipeapp.backend.entities.User;
import com.recipeapp.backend.repositories.FavouriteRecipeRepository;
import com.recipeapp.backend.repositories.RecipeRepository;
import com.recipeapp.backend.repositories.UserRepository;
import com.recipeapp.backend.services.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {

  @Autowired
  RecipeApi recipeApi;

  @Autowired
  FavouriteRecipeRepository favouriteRecipeRepository;

  @Autowired
  RecipeRepository recipeRepository;

  @Autowired
  UserRepository userRepository;

  @Override
  public Boolean addRecipeToFavourites(Long recipeId, UUID userId) {
    Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

    if (recipe == null) {
      recipe = this.createRecipeByFetchAPI(recipeId);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    if (favouriteRecipeRepository.existsByRecipeAndUser(recipe, user)) {
      throw new RuntimeException("Recipe is already saved as a favorite by the user");
    }

    System.out.println(recipe);
    System.out.println(user);

    // execute
    try {
      FavouriteRecipes favouriteRecipe = new FavouriteRecipes();
      favouriteRecipe.setRecipe(recipe);
      favouriteRecipe.setUser(user);
      favouriteRecipeRepository.save(favouriteRecipe);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Boolean removeRecipeFromFavourites(Long recipeId, UUID userId) {
    Recipe recipe = recipeRepository.findById(recipeId)
        .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    FavouriteRecipes favouriteRecipe = favouriteRecipeRepository.findByRecipeAndUser(recipe, user)
        .orElseThrow(() -> new RuntimeException("Recipe is not saved as a favorite by the user"));

    try {
      favouriteRecipeRepository.delete(favouriteRecipe);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public List<FavouriteRecipes> getSavedRecipesForUser(UUID userId) {
    return favouriteRecipeRepository.findByUserId(userId);
  }

  @Override
  public ResponseEntity<?> fetchRandomRecipeFromAPI() {
    return recipeApi.fetchRandomRecipe();
  }

  @Override
  public ResponseEntity<?> fetchRecipeByIdFromAPI(Long recipeId) {
    return recipeApi.fetchRecipeById(recipeId);
  }

  private Recipe createRecipeByFetchAPI(Long recipeId) {
    Recipe newRecipe = new Recipe();
    if (recipeId == null) {
      recipeId = generateRandomId();
    }
    newRecipe.setId(recipeId);
    newRecipe.setRecipeName(recipeApi.fetchRecipeName(recipeId));
    newRecipe.setRecipeImgUrl(recipeApi.fetchRecipeImgUrl(recipeId));

    return recipeRepository.save(newRecipe);
  }

  private Long generateRandomId() {
    return Math.abs(UUID.randomUUID().getMostSignificantBits());
  }

}
