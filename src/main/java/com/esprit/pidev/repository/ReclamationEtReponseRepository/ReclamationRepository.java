package com.esprit.pidev.repository.ReclamationEtReponseRepository;

import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    List<Reclamation> findByArchivedTrue();
    List<Reclamation> findByArchivedFalse();
    List<Reclamation> findByUserId(Long userId);
    List<Reclamation> findAllByOrderByDateReclamation();
    @Modifying
    @Transactional
    @Query("UPDATE Reclamation r SET r.archived = true WHERE r.etatReclamation = 'Non traitée' AND r.dateReclamation <= :dateLimite")
    void archiveReclamations(@Param("dateLimite") Date dateLimite);

    @Query("SELECT MONTH(r.dateReclamation) AS mois, COUNT(r) AS nbReclamations FROM Reclamation r GROUP BY mois")
    List<Object[]> countReclamationsByMonth();
}
