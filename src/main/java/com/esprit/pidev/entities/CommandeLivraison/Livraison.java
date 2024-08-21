package com.esprit.pidev.entities.CommandeLivraison;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private AdresseLivraison adresse;

    @Enumerated(EnumType.STRING)
    private EtatCommande etat;
    private String status;
    private String currentLocation;
    private LocalDateTime lastUpdatedAt;
    private String deliveryTimeSlot; // plage horaire de livraison
    private String alternateAddress; // adresse de livraison alternative
    private String collectionPoint; // point de collecte choisi
    private Double expressDeliveryFee; // frais de livraison express


    
}
