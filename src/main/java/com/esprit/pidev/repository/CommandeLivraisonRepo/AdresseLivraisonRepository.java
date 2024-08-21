package com.esprit.pidev.repository.CommandeLivraisonRepo;

import com.esprit.pidev.entities.CommandeLivraison.AdresseLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdresseLivraisonRepository extends JpaRepository<AdresseLivraison, Long> {
}
