package com.esprit.pidev.services.ReclamationEtReponseService;

import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import com.esprit.pidev.entities.ReclamationEtReponse.ReponseReclamation;

import java.util.List;

public interface IReponseReclamation {
    ReponseReclamation addReponseReclamation(ReponseReclamation repr, Long idReclamation);
    ReponseReclamation updateReponseReclamation(ReponseReclamation repr);
    ReponseReclamation retrieveReponseReclamationById(Long idReponseReclamation);
    List<ReponseReclamation> retrieveAllReponseReclamation();
    void deleteReponseReclamation(Long idReponseReclamation);
    //ReponseReclamation assignResponseReclamationToReclamation(Long idReponseReclamation, Long idReclamation);
}
