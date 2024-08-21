package com.esprit.pidev.services.ConseilRecetteServices;

import com.esprit.pidev.entities.ConseilRecette.Recette;

import java.util.List;

public interface IRecetteService {

    Recette addRecette(Recette recette);
    Recette updateRecette(Long id, Recette recette);
    Recette retrieveRecetteById(Long id);
    List<Recette> retrieveAllRecette();
    void deleteRecette(Long id);
}
