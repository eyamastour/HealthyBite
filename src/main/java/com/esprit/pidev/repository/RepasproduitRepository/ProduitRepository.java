package com.esprit.pidev.repository.RepasproduitRepository;

import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ProduitRepository extends JpaRepository<Produit,Long> {
    Set<Produit> findByUserId(Long userId);
    //@Query("SELECT COUNT(r) FROM Produit p JOIN p.reclamations r WHERE p.id = :idProduit")
    //int countReclamationsByProduit(@Param("idProduit") Long idProduit);
    //void checkReclamationsByProduit(Long id);
    @Modifying
    @Query("UPDATE Produit p SET p.bloquee= CASE WHEN SIZE(p.reclamations) > 2 THEN TRUE ELSE FALSE END WHERE p.id = :idProduit")
    void updateProduitBloqueStatus(@Param("idProduit") Long idProduit);

    @Modifying
    @Transactional
    @Query("UPDATE Produit r SET r.bloquee = true WHERE r.id IN " +
            "(SELECT rp.id FROM Produit rp JOIN rp.reclamations rc " +
            "GROUP BY rp HAVING COUNT(rc) > 3)")
    void blockProduitWithTooManyReclamations();
}
