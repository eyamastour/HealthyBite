package com.esprit.pidev.repository.ConseilRecette;

import com.esprit.pidev.entities.ConseilRecette.Ingredient;
import com.esprit.pidev.entities.ConseilRecette.Recette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findByRecette(Recette recette);
}
