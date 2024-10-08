package com.recipeapp.backend.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RecipeApi {

  private static final String RANDOM_RECIPE_URL = "https://www.themealdb.com/api/json/v1/1/random.php";
  private static final String LOOKUP_RECIPE_URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";

  public ResponseEntity<?> fetchRandomRecipe() {
    try {
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.getForEntity(RANDOM_RECIPE_URL, String.class);
      return ResponseEntity.ok(response.getBody());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch recipes");
    }
  }

  public ResponseEntity<?> fetchRecipeById(Long id) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.getForEntity(LOOKUP_RECIPE_URL + id, String.class);
      return ResponseEntity.ok(response.getBody());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch recipe.");
    }
  }

  // public String fetchRecipeName(Long id) {
  // try {
  // ResponseEntity<String> responseEntity = (ResponseEntity<String>)
  // fetchRecipeById(id);

  // // refactor
  // if (responseEntity.getStatusCode().is2xxSuccessful()) {
  // String responseBody = responseEntity.getBody();
  // ObjectMapper objectMapper = new ObjectMapper();
  // JsonNode rootNode = objectMapper.readTree(responseBody);
  // JsonNode mealsNode = rootNode.get("meals");
  // if (mealsNode != null && mealsNode.isArray() && mealsNode.size() > 0) {
  // JsonNode firstMealNode = mealsNode.get(0);
  // JsonNode strMealNode = firstMealNode.get("strMeal");
  // if (strMealNode != null) {
  // return strMealNode.asText();
  // }
  // }
  // }
  // return null;
  // } catch (Exception e) {
  // return null;
  // }
  // }

  // public String fetchRecipeImgUrl(Long id) {
  // try {
  // ResponseEntity<String> responseEntity = (ResponseEntity<String>)
  // fetchRecipeById(id);

  // // refactor
  // if (responseEntity.getStatusCode().is2xxSuccessful()) {
  // String responseBody = responseEntity.getBody();
  // ObjectMapper objectMapper = new ObjectMapper();
  // JsonNode rootNode = objectMapper.readTree(responseBody);
  // JsonNode mealsNode = rootNode.get("meals");
  // if (mealsNode != null && mealsNode.isArray() && mealsNode.size() > 0) {
  // JsonNode firstMealNode = mealsNode.get(0);
  // JsonNode strMealNode = firstMealNode.get("strMealThumb");
  // if (strMealNode != null) {
  // return strMealNode.asText();
  // }
  // }
  // }
  // return null;
  // } catch (Exception e) {
  // return null;
  // }
  // }

  private String parseJsonNode(JsonNode node, String fieldName) {
    if (node != null) {
      JsonNode fieldNode = node.get(fieldName);
      if (fieldNode != null) {
        return fieldNode.asText();
      }
    }
    return null;
  }

  public String fetchRecipeName(Long id) {
    try {
      ResponseEntity<String> responseEntity = (ResponseEntity<String>) fetchRecipeById(id);
      if (responseEntity.getStatusCode().is2xxSuccessful()) {
        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode mealsNode = rootNode.get("meals");
        if (mealsNode != null && mealsNode.isArray() && mealsNode.size() > 0) {
          JsonNode firstMealNode = mealsNode.get(0);
          return parseJsonNode(firstMealNode, "strMeal");
        }
      }
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  public String fetchRecipeImgUrl(Long id) {
    try {
      ResponseEntity<String> responseEntity = (ResponseEntity<String>) fetchRecipeById(id);
      if (responseEntity.getStatusCode().is2xxSuccessful()) {
        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode mealsNode = rootNode.get("meals");
        if (mealsNode != null && mealsNode.isArray() && mealsNode.size() > 0) {
          JsonNode firstMealNode = mealsNode.get(0);
          return parseJsonNode(firstMealNode, "strMealThumb");
        }
      }
      return null;
    } catch (Exception e) {
      return null;
    }
  }
}
