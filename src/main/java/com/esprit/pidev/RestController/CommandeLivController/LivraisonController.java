package com.esprit.pidev.RestController.CommandeLivController;

import com.esprit.pidev.entities.CommandeLivraison.Commande;
import com.esprit.pidev.entities.CommandeLivraison.EtatCommande;
import com.esprit.pidev.entities.CommandeLivraison.Livraison;


import com.esprit.pidev.services.CommandeLivraisonServices.LivraisonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/test")
public class LivraisonController {
    @Autowired
    private LivraisonService livraisonService;

    @PostMapping("/addLivraison")
    public ResponseEntity<Livraison> addLivraison(@RequestBody Livraison livraison) {
        Livraison newLivraison = livraisonService.addLivraison(livraison);
        return new ResponseEntity<>(newLivraison, HttpStatus.CREATED);
    }

    @GetMapping("/getLivraisonById/{id}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable("id") Long id) {
        Livraison livraison = livraisonService.getLivraisonById(id);
        return new ResponseEntity<>(livraison, HttpStatus.OK);
    }


    @PutMapping("/updateLivraison/{id}")
    public void updateLivraison(@PathVariable Long id, @RequestBody Livraison livraison) {
        livraison.setId(id);
        livraisonService.updateLivraison(livraison);
    }

    @DeleteMapping("/deleteLivraisonById/{id}")
    public void deleteLivraisonById(@PathVariable Long id) {livraisonService.deleteLivraisonById(id);}


    @PutMapping("/accepter/{id}")
    public ResponseEntity<Livraison> accepterLivraison(@PathVariable("id") Long id) {
        Livraison livraison = livraisonService.getLivraisonById(id);
        livraison.setEtat(EtatCommande.CONFIRMEE);
        Livraison updatedLivraison = livraisonService.updateLivraison(livraison);
        return new ResponseEntity<>(updatedLivraison, HttpStatus.OK);
    }

    @PutMapping("/refuser/{id}")
    public ResponseEntity<Livraison> refuserLivraison(@PathVariable("id") Long id) {
        Livraison livraison = livraisonService.getLivraisonById(id);
        livraison.setEtat(EtatCommande.ANNULEE);
        Livraison updatedLivraison = livraisonService.updateLivraison(livraison);
        return new ResponseEntity<>(updatedLivraison, HttpStatus.OK);
    }
    @PutMapping("/deliveryTimeSlot/{id}")
    public Livraison updateDeliveryTimeSlot(@PathVariable Long id, @RequestBody String deliveryTimeSlot) {
        return livraisonService.updateLivraisonTimeSlot(id, deliveryTimeSlot);
    }
    @PutMapping("/{id}/collectionPoint")
    public Livraison updateCollectionPoint(@PathVariable Long id, @RequestBody String collectionPoint) {
        return livraisonService.updateCollectionPoint(id, collectionPoint);
    }
    @GetMapping("/findByEtatAndDeliveryTimeSlot")
    public ResponseEntity<List<Livraison>> findLivraisonsByEtatAndDeliveryTimeSlot(@RequestParam EtatCommande etatCommande,
                                                                                   @RequestParam String deliveryTimeSlot) {
        List<Livraison> livraisons = livraisonService.findLivraisonsByEtatAndDeliveryTimeSlot(etatCommande, deliveryTimeSlot);
        return ResponseEntity.ok(livraisons);
    }
}