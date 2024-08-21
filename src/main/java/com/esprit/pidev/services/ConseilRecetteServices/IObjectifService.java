package com.esprit.pidev.services.ConseilRecetteServices;


import com.esprit.pidev.entities.ConseilRecette.Objectif;

import java.util.List;
import java.util.Optional;

public interface IObjectifService {
    Objectif addObjectif(Objectif objectif);
    Objectif addObjectif(Objectif objectif, Long userId);
    Objectif updateObjectif(Long id, Objectif objectif);
    Optional<Objectif> retrieveObjectifById(Long id);
    List<Objectif> retrieveAllObjectif();
    void deleteObjectif(Long id);
    List<Objectif> findSimilarObjectifs(Long poidDepart, Long objectifPoid);
}
