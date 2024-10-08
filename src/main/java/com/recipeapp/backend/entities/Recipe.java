package com.recipeapp.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "recipe")
public class Recipe {
  @Id
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "recipe_name")
  private String recipeName;

  @NotNull
  @Column(name = "recipe_img")
  private String recipeImgUrl;
}
