package com.esprit.pidev.services.ReclamationEtReponseService;

import com.esprit.pidev.entities.ReclamationEtReponse.Notification;
import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import com.esprit.pidev.entities.ReclamationEtReponse.ReponseReclamation;
import com.esprit.pidev.repository.ReclamationEtReponseRepository.ReponseReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReponseReclamationService implements IReponseReclamation {
    NotificationService notificationService;
    ReclamationService reclamationService;
    ReponseReclamationRepository reponseReclamationRepository;

    @Override
    public ReponseReclamation addReponseReclamation(ReponseReclamation repr, Long idReclamation) {
        Reclamation rec = reclamationService.retrieveReclamationById(idReclamation);
        Notification not = new Notification();
        not.setTextNotification("Votre Reclamation a ete traitee");
        not.setDateNotification(repr.getDateReponseReclamation());
        notificationService.addNotification(not);
        rec.setNotifications(not);
        rec.setReponseReclamation(repr);
        rec.setEtatReclamation("Traitee");
        return reponseReclamationRepository.save(repr);
    }

    @Override
    public ReponseReclamation updateReponseReclamation(ReponseReclamation repr) {
        return reponseReclamationRepository.save(repr);
    }

    @Override
    public ReponseReclamation retrieveReponseReclamationById(Long idReponseReclamation) {
        return reponseReclamationRepository.findById(idReponseReclamation).orElse(null);
    }

    @Override
    public List<ReponseReclamation> retrieveAllReponseReclamation() {
        return reponseReclamationRepository.findAll();
    }

    @Override
    public void deleteReponseReclamation(Long idReponseReclamation) {
        reponseReclamationRepository.deleteById(idReponseReclamation);
    }
}

    // @Override
    //public ReponseReclamation assignResponseReclamationToReclamation(Long idReponseReclamation, Long idReclamation) {
    //  ReponseReclamation repr = reponseReclamationRepository.findById(idReponseReclamation).orElse(null);
    //Reclamation rec = reclamationRepository.findById(idReclamation).orElse(null);
    //repr.getReclamation().getDateReclamation();
    //repr.getReclamation().getTextReclamation();
    //repr.getReclamation().setEtatReclamation("Traitéé");
    //return reponseReclamationRepository.save(repr);
    //}

    // public class Stats {
    // private int traité;
    //private int nonTraité;

    //public Stats() {
    // this.traité = 0;
    //this.nonTraité = 0;
    //}
    //public Stats(int traité, int nonTraité) {
    //  this.traité = traité;
    // this.nonTraité = nonTraité;
    //}
    //}
    //@GetMapping("/pie-chart")
    //public String getPieChart(Model model){
    // List<Reclamation> réclamations = iReclamation.retrieveAllReclamation();
    //int traitéCount = 0;
    //int nonTraitéCount = 0;
    //for (Reclamation réclamation : réclamations) {
    //  if (réclamation.getEtatReclamation().equals("traite")) {
    //    traitéCount++;
    //} else {
    //  nonTraitéCount++;
    //}
    //}
    //Stats stats = new Stats (traitéCount, nonTraitéCount);
    //model.addAttribute("stats", stats);
    //return "pie-chart";
    // }

