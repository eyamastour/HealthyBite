package com.esprit.pidev.services.RepasProduitServices;

import com.esprit.pidev.entities.ProduitRepas.Nutrition;

import java.util.List;

public interface INutrition {
    Nutrition addNutrition(Nutrition nut);
    Nutrition updateNutrition(Nutrition nut);
    Nutrition retrieveNutritionById(Long id);
    List<Nutrition> retrieveAllNutrition();
    void deleteNutrition(Long id);
}
