package com.esprit.pidev.services.ConseilRecetteServices;


import com.esprit.pidev.entities.ConseilRecette.Conseil;
import com.esprit.pidev.entities.ConseilRecette.Objectif;
import com.esprit.pidev.entities.UserRole.User;

import com.esprit.pidev.repository.ConseilRecette.ConseilRepository;
import com.esprit.pidev.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ConseilServiceImpl implements IConseilService {

    @Autowired
    private ConseilRepository conseilRepository;

    @Autowired
    private IObjectifService objectifService;

    @Autowired
    private UserService userService;


    @Override
    public Conseil addConseil(Conseil conseil) {
        return conseilRepository.save(conseil);
    }

    @Override
    public Conseil addConseil(Conseil conseil, Long objectifId) {
        Objectif o = objectifService.retrieveObjectifById(objectifId).orElseThrow(() -> new NoSuchElementException("Objectif " + objectifId + " not found"));
        conseil.setObjectif(o);
        return conseilRepository.save(conseil);
    }

    @Override
    public Conseil updateConseil(Long id, Conseil conseil) {
        Conseil existingConseil = conseilRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Conseil with id " + id + " not found"));
        if (conseil.getDescription() == null) {
            throw new IllegalArgumentException("No description is provided");
        }
        existingConseil.setDescription(conseil.getDescription());

        return conseilRepository.save(existingConseil);
    }



    @Override
    public Optional<Conseil> retrieveConseilById(Long id) {
        return conseilRepository.findById(id);
    }

    @Override
    public List<Conseil> retrieveAllConseil() {
        return conseilRepository.findAll();
    }

    @Override
    public void deleteConseil(Long id) {
        conseilRepository.deleteById(id);
    }

    @Override
    public List<Conseil> recommend(Long id) {

        User u = userService.retrieveUserById(id);

        List<Objectif> similarObjectifs = new ArrayList<>();
        u.getObjectifs().forEach(objectif -> {
            List<Objectif> fetched = objectifService.findSimilarObjectifs(objectif.getPoidDepart(), objectif.getObjectifPoid());
            if (fetched.size() == 0) {
                return;
            }

                similarObjectifs.addAll(fetched);
        });
        List<Conseil> conseils = new ArrayList<>();
        for (Objectif obj : similarObjectifs) {
            conseils.addAll(obj.getConseils());
        }
        return conseils;
    }


}
