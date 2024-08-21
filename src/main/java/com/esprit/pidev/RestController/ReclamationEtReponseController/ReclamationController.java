package com.esprit.pidev.RestController.ReclamationEtReponseController;

import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import com.esprit.pidev.repository.ReclamationEtReponseRepository.ReclamationRepository;
import com.esprit.pidev.services.ReclamationEtReponseService.IReclamation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/test")
public class ReclamationController {
    IReclamation iReclamation;
    ReclamationRepository reclamationRepository;

    @PostMapping("/assignRepasToReclamation/{idRepas}")
    public void assignRepasToReclamation( @RequestBody Reclamation rec , @PathVariable("idRepas")Long idRepas) {
        iReclamation.assignRepasToReclamation( rec, idRepas);
    }
    @PostMapping("/addReclamation/{idUser}")
    public void addReclamation( @RequestBody Reclamation rec ,@PathVariable("idUser") Long idUser) {
        iReclamation.addReclamation( rec, idUser);
    }

    @PostMapping("/assignProduitToReclamation/{idProduit}")
    public void assignProduitToReclamation( @RequestBody Reclamation rec ,@PathVariable("idProduit")Long idProduit) {
        iReclamation.assignProduitToReclamation(rec, idProduit);
    }

    @PutMapping("/updateReclamation")
    public Reclamation updateReclamation(@RequestBody Reclamation rec) {
        return iReclamation.updateReclamation(rec);
    }

    @GetMapping("/retrieveReclamationById/{idReclamation}")
    public Reclamation retrieveReclamationById(@PathVariable("idReclamation") Long idReclamation) {
        return iReclamation.retrieveReclamationById(idReclamation);
    }

    @GetMapping("/retrieveAllReclamation")
    public List<Reclamation> retrieveAllReclamation() {
        return iReclamation.retrieveAllReclamation();
    }

    @DeleteMapping("/ArchiverReclamation/{idReclamation}")
    public void ArchiverReclamation(@PathVariable("idReclamation") Long idReclamation) {
        iReclamation.ArchiverReclamation(idReclamation);
    }




    @GetMapping("/archived-reclamations")
    public List<Reclamation> getArchivedReclamations(Model model) {
        List<Reclamation> archivedReclamations = reclamationRepository.findByArchivedTrue();
        model.addAttribute("archivedReclamations", archivedReclamations);
        return archivedReclamations;
    }
    @PutMapping("/archiverReclamationNonTraitee")
    public void archiveReclamationsNonTraitees(){
        iReclamation.archiveReclamationsNonTraitees();
    }

    @GetMapping("/retrieveReclamation")
    public List<Reclamation> retrieveReclamation(boolean archived) {
        return iReclamation.retrieveReclamation(archived);
    }

    @GetMapping("/retrieveAllReclamationByUser/{id}")
    public List<Reclamation> retrieveAllReclamationByUser(@PathVariable("id") long userId) {
        return iReclamation.retrieveAllReclamationByUser(userId);
    }

    @GetMapping("/afficherReclamationsTries")
    public List<Reclamation> afficherReclamationsTries(Model model) {
        List<Reclamation> reclamations = reclamationRepository.findAllByOrderByDateReclamation();
        model.addAttribute("reclamations", reclamations);
        return reclamations;
    }

    @GetMapping("/reclamations/mois")
    public List<Object[]> countReclamationsByMonth() {
        return iReclamation.countReclamationsByMonth();
    }
}



//@PostMapping("/assignNotificationToReclamation/{idReclamation}/{idNotification}")
//public void assignNotificationToReclamation(@PathVariable("idReclamation") Long idReclamation,@PathVariable("idNotification") Long idNotification){
//  iReclamation.assignNotificationToReclamation(idReclamation,idNotification);
//}

    //@PostMapping("/assignReclamationToUser/{idReclamation}/{id}")
    //public void assignReclamationToUser(@PathVariable("idReclamation") Long idReclamation,@PathVariable("id") Long id){
    // iReclamation.assignReclamationToUser(idReclamation,id);
    //}


