package com.esprit.pidev.entities.ProduitRepas;

import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import com.esprit.pidev.entities.UserRole.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Base64;
import java.util.Set;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Repas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private double prix;
    private String ingredient;
    private String allergene;
    private LocalDateTime dateAjout;
    @Enumerated(EnumType.STRING)
    private  ObjectifType objectif;

    @Column(name = "image_data")
    @Lob
    private byte[] imageData;


    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_path")
    private String imagePath;

    public String getImageBase64() {
        if (imageData == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(imageData);
    }

    public void setImageBase64(String imageBase64) {
        if (imageBase64 == null) {
            imageData = null;
        } else {
            imageData = Base64.getDecoder().decode(imageBase64);
        }
    }


    private Boolean bloquee=false;

    @Enumerated(EnumType.STRING)
    private CategRepas CategorieRep;
    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "repas", cascade = CascadeType.ALL)
    private Nutrition nutrition;

    private int quantite;
    @OneToMany(mappedBy = "repas",cascade = CascadeType.ALL)
    private Set<Reclamation> reclamations;
}
