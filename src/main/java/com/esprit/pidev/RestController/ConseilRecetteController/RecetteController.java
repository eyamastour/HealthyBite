package com.esprit.pidev.RestController.ConseilRecetteController;

import com.esprit.pidev.entities.ConseilRecette.Recette;
import com.esprit.pidev.services.ConseilRecetteServices.IRecetteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/recette")
public class RecetteController {
    @Autowired

    private IRecetteService recetteService;

    @PostMapping("/")
    public ResponseEntity<Recette> addRecette(@RequestBody Recette recette){

        Recette saved = recetteService.addRecette(recette);
        return new ResponseEntity<Recette>(saved, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Recette> updateRecette(@PathVariable("id")Long id , @RequestBody Recette recette) {
        Recette updated = recetteService.updateRecette(id,recette);
        return new ResponseEntity<Recette>(updated, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Recette> findRecetteById(@PathVariable(value = "id") Long id) {
        Recette recette = recetteService.retrieveRecetteById(id);
        return new ResponseEntity<Recette>(recette, HttpStatus.FOUND);
    }


    @GetMapping(value = "/")
    public ResponseEntity<Collection<Recette>> getAllRecettes() {
        Collection<Recette> recettes = recetteService.retrieveAllRecette();
        return new ResponseEntity<Collection<Recette>>(recettes, HttpStatus.FOUND);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecette(@PathVariable(value = "id", required = true) Long id) {
        recetteService.deleteRecette(id);
        return new ResponseEntity<Void>(HttpStatus.GONE);
    }
}

