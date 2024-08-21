package com.esprit.pidev.repository.CommandeLivraisonRepo;

import com.esprit.pidev.entities.CommandeLivraison.Commande;
import com.esprit.pidev.entities.CommandeLivraison.EtatCommande;
import com.esprit.pidev.entities.UserRole.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByEtatCommande(EtatCommande etatCommande);
    /*@Query("SELECT c FROM Commande c WHERE c.dateCommande BETWEEN :startDate AND :endDate AND c.etatCommande = :etatCommande")
    List<Commande> getCommandesByDateAndEtat(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("etatCommande") EtatCommande etatCommande);
    */
    List<Commande> findByDateCommandeBetweenAndEtatCommande(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("etatCommande") EtatCommande etatCommande);

}