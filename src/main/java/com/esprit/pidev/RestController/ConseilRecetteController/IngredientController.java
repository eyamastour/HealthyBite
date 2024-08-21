package com.esprit.pidev.RestController.ConseilRecetteController;

import com.esprit.pidev.entities.ConseilRecette.Ingredient;
import com.esprit.pidev.entities.ConseilRecette.Recette;
import com.esprit.pidev.exceptions.RecetteNotFoundException;
import com.esprit.pidev.services.ConseilRecetteServices.IIngredientService;
import com.esprit.pidev.services.ConseilRecetteServices.IRecetteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

     private final IIngredientService ingredientService;

     private final IRecetteService recetteService;
    @Autowired
    public IngredientController(IIngredientService ingredientService, IRecetteService recetteService) {
        this.ingredientService = ingredientService;
        this.recetteService = recetteService;
    }

    @PostMapping("/recette/{recetteId}/ingredients")
    public ResponseEntity<Ingredient> addIngredient(
            @PathVariable("recetteId")Long recetteId, @RequestBody Ingredient ingredient){
        Recette recette = recetteService.retrieveRecetteById(recetteId);
        if(recette == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recette not found");
        }
        ingredient.setRecette(recette);
        Ingredient saved = ingredientService.addIngredient(ingredient, recetteId);
        return new ResponseEntity<Ingredient>(saved, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable("id")Long id ,@RequestBody Ingredient ingredient) {
        Ingredient updated = ingredientService.updateIngredient(id, ingredient);
        return new ResponseEntity<Ingredient>(updated, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Ingredient> findIngredientById(@PathVariable(value = "id") Long id) {
        Optional<Ingredient> ingredient = ingredientService.retrieveIngredientById(id);
        return new ResponseEntity<Ingredient>(ingredient.get(), HttpStatus.FOUND);
    }


    @GetMapping(value = "/")
    public ResponseEntity<Collection<Ingredient>> getAllIngredients() {
        Collection<Ingredient> ingredients = ingredientService.retrieveAllIngredient();
        return new ResponseEntity<Collection<Ingredient>>(ingredients, HttpStatus.FOUND);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable(value = "id", required = true) Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<Void>(HttpStatus.GONE);
    }


    @GetMapping("/recetteIngredients")
    public ResponseEntity<List<Ingredient>> getRecetteIngredients(@RequestParam("recetteId")Long recetteId){
        Recette recette = recetteService.retrieveRecetteById(recetteId);
        List<Ingredient> ingredients = ingredientService.retrieveIngredientByRecette(recette);
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/recette/{recetteId}/ingredients")
    public List<Ingredient>  getIngredientsByRecetteId(@PathVariable("recetteId") Long recetteId){
        Recette recette = recetteService.retrieveRecetteById(recetteId);
        if(recette == null){
            throw new RecetteNotFoundException("Recette not Found");
        }
        return recette.getIngredients();
    }


}
