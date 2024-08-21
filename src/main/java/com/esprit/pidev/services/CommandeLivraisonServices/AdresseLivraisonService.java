package com.esprit.pidev.services.CommandeLivraisonServices;

import com.esprit.pidev.entities.CommandeLivraison.AdresseLivraison;
import com.esprit.pidev.repository.CommandeLivraisonRepo.AdresseLivraisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdresseLivraisonService implements IAdresseLivraisonService {

    @Autowired
    private AdresseLivraisonRepository adresseLivraisonRepository;

    @Override
    public AdresseLivraison addAdresseLivraison(AdresseLivraison adresseLivraison) {
        return adresseLivraisonRepository.save(adresseLivraison);
    }


    @Override
    public AdresseLivraison updateAdresseLivraison(AdresseLivraison adresseLivraison) {
        return adresseLivraisonRepository.save(adresseLivraison);
    }

    @Override
    public void deleteAdresseLivraisonById(Long id) {
        adresseLivraisonRepository.deleteById(id);
    }

    @Override
    public AdresseLivraison getAdresseLivraisonById(Long id) {
         ;
        return adresseLivraisonRepository.findById(id).orElse(null);
    }

    @Override
    public List<AdresseLivraison> getAllAdressesLivraison() {
        return adresseLivraisonRepository.findAll();
    }
    @Override
    public boolean existAdresseLivraison(Long id) {
        return adresseLivraisonRepository.existsById(id);
    }

}
