
package com.esprit.pidev.services.CommandeLivraisonServices;

import com.esprit.pidev.entities.CommandeLivraison.Commande;
import com.esprit.pidev.entities.CommandeLivraison.EtatCommande;
import com.esprit.pidev.entities.CommandeLivraison.Livraison;

import com.esprit.pidev.repository.CommandeLivraisonRepo.LivraisonRepository;
import com.esprit.pidev.services.CommandeLivraisonServices.ILivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LivraisonService implements ILivraisonService {

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Override
    public Livraison addLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    @Override
    public Livraison updateLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }


    @Override
    public void deleteLivraisonById(Long id) {
        livraisonRepository.deleteById(id);
    }


    @Override
    public Livraison getLivraisonById(Long id) {
        Optional<Livraison> livraisonOptional = livraisonRepository.findById(id);
        return livraisonOptional.orElse(null);
    }

    @Override
    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }

    @Override
    public List<Livraison> getLivraisonsByEtatCommande(String etatCommande) {
        return livraisonRepository.findByEtat(EtatCommande.valueOf(etatCommande.toUpperCase()));
    }


    @Override
    public Livraison accepterLivraison(Long id) {
        Livraison livraison = getLivraisonById(id);
        livraison.setEtat(EtatCommande.CONFIRMEE);
        return this.updateLivraison(livraison);
    }

    @Override
    public Livraison refuserLivraison(Long id) {
        Livraison livraison = getLivraisonById(id);
        livraison.setEtat(EtatCommande.ANNULEE);
        return updateLivraison(livraison);
    }
    @Override
    public Livraison updateLivraisonTimeSlot(Long id, String deliveryTimeSlot) {
        Livraison livraison = getLivraisonById(id);
        livraison.setDeliveryTimeSlot(deliveryTimeSlot);
        return updateLivraison(livraison);
    }
    @Override
    public Livraison updateCollectionPoint(Long id, String pointCollecte) {
        Livraison livraison = getLivraisonById(id);

        livraison.setCollectionPoint(pointCollecte);

        return  updateLivraison(livraison);
    }
    public List<Livraison> findLivraisonsByEtatAndDeliveryTimeSlot(EtatCommande etatCommande, String deliveryTimeSlot) {
        return livraisonRepository.findLivraisonsByEtatAndDeliveryTimeSlot(etatCommande, deliveryTimeSlot);
    }

}
