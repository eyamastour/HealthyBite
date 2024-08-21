package com.esprit.pidev.entities.CommandeLivraison;

import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import com.esprit.pidev.entities.UserRole.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCom;



    @OneToMany
    @JsonIgnore
    private List<Produit> Produit;
    @OneToMany
    @JsonIgnore
    private List<Repas> Repas;

    @OneToOne
    @JsonIgnore
    private Livraison livraison;

    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    private Date dateCommande;

    private Integer quantite;
    private double total;

    @ManyToOne
    @JsonIgnore
    private User user;



}
