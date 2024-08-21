package com.esprit.pidev.entities.ConseilRecette;

import com.esprit.pidev.entities.ConseilRecette.Ingredient;
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

public class Recette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy="recette", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ingredient> ingredients= new ArrayList<>();




    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}


