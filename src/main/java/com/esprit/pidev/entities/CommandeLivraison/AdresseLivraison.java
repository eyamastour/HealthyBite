package com.esprit.pidev.entities.CommandeLivraison;

import com.esprit.pidev.entities.UserRole.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdresseLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idAdr;

    private String rue;

    private String ville;

    private String num;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;



}
