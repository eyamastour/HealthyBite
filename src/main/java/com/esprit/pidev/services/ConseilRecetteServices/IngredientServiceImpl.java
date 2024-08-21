package com.esprit.pidev.services.ConseilRecetteServices;

import com.esprit.pidev.entities.ConseilRecette.Ingredient;
import com.esprit.pidev.entities.ConseilRecette.Recette;
import com.esprit.pidev.exceptions.IngredientNotFoundException;
import com.esprit.pidev.exceptions.RecetteNotFoundException;
import com.esprit.pidev.repository.ConseilRecette.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IIngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;


    @Autowired
    private IRecetteService recetteService;

    @Override
    public Ingredient addIngredient(Ingredient ingredient, Long recetteId) {
        Recette recette = recetteService.retrieveRecetteById(recetteId);
        if(recette == null){
            throw new RecetteNotFoundException("Recette not found");
        }
        ingredient.setRecette(recette);
         return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        Ingredient existigIngredient = ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException("Ingredient with id " + id + " not found"));
        existigIngredient.setCalories(ingredient.getCalories());
        if(ingredient.getNom()!=null){
            existigIngredient.setNom(ingredient.getNom());
        }
        if(ingredient.getQuantite()!=0.0f){
            existigIngredient.setQuantite(ingredient.getQuantite());
        }
        if(ingredient.getRecette()!=null){
            existigIngredient.setRecette(ingredient.getRecette());
        }
        return ingredientRepository.save(existigIngredient);
    }

    @Override
    public Optional<Ingredient> retrieveIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public List<Ingredient> retrieveAllIngredient() {
        return ingredientRepository.findAll();
    }

    @Override
    public void deleteIngredient(Long id) {
        this.ingredientRepository.deleteById(id);
    }

    @Override
    public List<Ingredient> retrieveIngredientByRecette(Recette recette) {
        return null;
    }
}
