package com.esprit.pidev.services.ConseilRecetteServices;

import com.esprit.pidev.entities.ConseilRecette.Recette;
import com.esprit.pidev.repository.ConseilRecette.RecetteRepository;
import com.esprit.pidev.services.ConseilRecetteServices.IRecetteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecetteServiceImpl implements IRecetteService {

    @Autowired
    private RecetteRepository recetteRepository;

    @Override
    public Recette addRecette(Recette recette) {
        return recetteRepository.save(recette);
    }

    @Override
    public Recette updateRecette(Long id, Recette recette) {
        Recette existingRecette = recetteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Recette with id " + id + " not found"));
        if (recette.getNom() == null || recette.getIngredients() == null) {
            throw new IllegalArgumentException("no args to update recette");
        }
        existingRecette.setNom(recette.getNom());
        existingRecette.setIngredients(recette.getIngredients());

        return recetteRepository.save(existingRecette);
    }


    @Override
    public Recette retrieveRecetteById(Long id) {
        return recetteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Post with id " + id + " not found"));

    }

    @Override
    public List<Recette> retrieveAllRecette() {
        return recetteRepository.findAll();
    }

    @Override
    public void deleteRecette(Long id) {
        recetteRepository.deleteById(id);
    }
}
