package com.recipeapp.backend.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  @Column(name = "id")
  private UUID id;

  @NotNull
  @Column(name = "username")
  private String username;

  @NotNull
  @Column(name = "email")
  private String email;

  @NotNull
  @Column(name = "password")
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @NotNull
  @Column(name = "created_at")
  @DateTimeFormat(pattern = "yyy-MM-dd")
  private LocalDate createdAt;

  @Column(name = "updated_at")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate updatedAt;

  // Relation with Recipes - A user can have MANY recipes.
  // @OneToMany(targetEntity = Recipe.class, mappedBy = "user", cascade = CascadeType.REMOVE)
  // @JsonIgnore
  // private List<Recipe> recipes;
}
