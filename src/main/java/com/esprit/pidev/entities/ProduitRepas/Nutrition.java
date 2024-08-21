package com.esprit.pidev.entities.ProduitRepas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Nutrition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double energies;
    private double acides;
    private double glucides;
    private double sucres;
    private double fibre;
    private double calories;
    private double proteines;
    private double lipides;
    private double sel;

    @OneToOne
    @JsonIgnore
    private Produit produit;

    @OneToOne
    @JsonIgnore
    private Repas repas;

}
