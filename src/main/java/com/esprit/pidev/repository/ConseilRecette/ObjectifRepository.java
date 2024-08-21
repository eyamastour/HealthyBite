package com.esprit.pidev.repository.ConseilRecette;

import com.esprit.pidev.entities.ConseilRecette.Objectif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectifRepository extends JpaRepository<Objectif, Long> {

    @Query(value = "SELECT * FROM Objectif WHERE  poid_depart BETWEEN ?1 and ?2 AND objectif_poid BETWEEN  ?3  AND ?4", nativeQuery = true)
    public List<Objectif> findByPoidDeDepardWithTolerance(long poidDepart,long poidDepartTolerated, long objectifPoid, long objectifPoidTolerated);


}
