package com.esprit.pidev.services.ConseilRecetteServices;

import com.esprit.pidev.entities.ConseilRecette.Conseil;

import java.util.List;
import java.util.Optional;

public interface IConseilService {

    Conseil addConseil(Conseil conseil);
    Conseil addConseil(Conseil conseil, Long objectifId);

    Conseil updateConseil(Long id, Conseil conseil);
    Optional<Conseil> retrieveConseilById(Long id);
    List<Conseil> retrieveAllConseil();
    void deleteConseil(Long id);
    List<Conseil> recommend(Long id);
}
