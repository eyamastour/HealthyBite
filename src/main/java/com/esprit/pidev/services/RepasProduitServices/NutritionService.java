package com.esprit.pidev.services.RepasProduitServices;

import com.esprit.pidev.entities.ProduitRepas.Nutrition;
import com.esprit.pidev.repository.RepasproduitRepository.NutritionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NutritionService implements INutrition{
    NutritionRepository nutritionRepository;

    @Override
    public Nutrition addNutrition(Nutrition nut) {
        return nutritionRepository.save(nut);
    }

    @Override
    public Nutrition updateNutrition(Nutrition nut) {
        return nutritionRepository.save(nut);
    }

    @Override
    public Nutrition retrieveNutritionById(Long id) {
        return nutritionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Nutrition> retrieveAllNutrition() {
        return nutritionRepository.findAll();
    }

    @Override
    public void deleteNutrition(Long id) {
        nutritionRepository.deleteById(id);
    }
}
