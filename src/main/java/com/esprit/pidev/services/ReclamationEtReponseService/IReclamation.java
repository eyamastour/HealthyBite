package com.esprit.pidev.services.ReclamationEtReponseService;

import com.esprit.pidev.entities.ReclamationEtReponse.Notification;
import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import com.esprit.pidev.entities.ReclamationEtReponse.ReponseReclamation;
import com.esprit.pidev.entities.UserRole.User;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IReclamation {

    Reclamation addReclamation(Reclamation rec , Long id );
    Reclamation updateReclamation(Reclamation rec);
    Reclamation retrieveReclamationById(Long idReclamation);
    List<Reclamation> retrieveAllReclamation();
    List<Reclamation> retrieveAllReclamationByUser(long id);
    List<Reclamation> retrieveReclamation(boolean archived);
    void ArchiverReclamation(Long idReclamation);
    public Reclamation assignRepasToReclamation(Reclamation rec ,Long id);
    public Reclamation assignProduitToReclamation(Reclamation rec,Long id);

    void archiveReclamationsNonTraitees();
    List<Object[]> countReclamationsByMonth();
    public List<Reclamation> recupererReclamationsTrieesParDate();
    //void assignReclamationToUser(Long idReclamation, Long id);



}
