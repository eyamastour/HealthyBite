package com.esprit.pidev.repository.CommandeLivraisonRepo;

import com.esprit.pidev.entities.CommandeLivraison.Commande;
import com.esprit.pidev.entities.CommandeLivraison.EtatCommande;
import com.esprit.pidev.entities.CommandeLivraison.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
    List<Livraison> findByEtat(EtatCommande etat);
    @Query("SELECT l FROM Livraison l WHERE l.etat = :etat AND l.deliveryTimeSlot = :deliveryTimeSlot")
    List<Livraison> findLivraisonsByEtatAndDeliveryTimeSlot(@Param("etat") EtatCommande etatCommande, @Param("deliveryTimeSlot") String deliveryTimeSlot);
}
