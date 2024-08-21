package com.esprit.pidev.entities.ConseilRecette;

import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.UserRole.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Objectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long poidDepart;
        private Long poidActuel;
    private Long taille;
    private Long objectifPoid;

    private TypeActivite typeActivite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "objectif", cascade = CascadeType.ALL)
    private List<Conseil> conseils = new ArrayList<>();


}
