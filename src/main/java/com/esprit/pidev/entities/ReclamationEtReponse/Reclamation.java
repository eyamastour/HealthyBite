package com.esprit.pidev.entities.ReclamationEtReponse;

import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import com.esprit.pidev.entities.UserRole.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReclamation;

    @Temporal(TemporalType.DATE )
    private Date dateReclamation;
    private String textReclamation;

    private String etatReclamation;
    private Boolean archived=false;

    @OneToOne
    @JsonIgnore
    private Notification notifications;

    @OneToOne
    private ReponseReclamation reponseReclamation;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Repas repas;

    @ManyToOne
    @JsonIgnore
    private Produit produit;

}
