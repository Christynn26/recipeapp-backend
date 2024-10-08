package com.recipeapp.backend.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipeapp.backend.entities.FavouriteRecipes;
import com.recipeapp.backend.entities.Recipe;
import com.recipeapp.backend.entities.User;

@Repository
public interface FavouriteRecipeRepository extends JpaRepository<FavouriteRecipes, UUID> {
  boolean existsByRecipeAndUser(Recipe recipe, User user);

  List<FavouriteRecipes> findByUserId(UUID userId);

  Optional<FavouriteRecipes> findByRecipeAndUser(Recipe recipe, User user);
}
