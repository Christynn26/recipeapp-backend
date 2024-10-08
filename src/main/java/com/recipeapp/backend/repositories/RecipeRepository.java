package com.recipeapp.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipeapp.backend.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
  
}
