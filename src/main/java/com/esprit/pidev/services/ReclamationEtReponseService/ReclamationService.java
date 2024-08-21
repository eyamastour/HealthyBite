package com.esprit.pidev.services.ReclamationEtReponseService;

import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import com.esprit.pidev.entities.ReclamationEtReponse.Reclamation;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.repository.ReclamationEtReponseRepository.ReclamationRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import com.esprit.pidev.security.services.UserService;
import com.esprit.pidev.services.RepasProduitServices.ProduitService;
import com.esprit.pidev.services.RepasProduitServices.RepasService;
import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReclamationService implements IReclamation {

    ReclamationRepository reclamationRepository;
    INotification notificationService;

    UserService userService;
    RepasService repasService;
    ProduitService produitService;
    UserRepository userRepository;


    @Override
    public Reclamation assignRepasToReclamation(Reclamation rec,Long id){
        Repas repas = repasService.retrieveRepasById(id);
        rec.setRepas(repas);
        rec.setUser(getCurrentUser());
        return reclamationRepository.save(rec);
    }

    @Override
    public Reclamation assignProduitToReclamation(Reclamation rec,Long id) {
        Produit produit = produitService.retrieveProduitById(id);
        rec.setProduit(produit);
        rec.setUser(getCurrentUser());
        return reclamationRepository.save(rec);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


        @Scheduled(cron = "0 0 0 * * *")
        public void archiveReclamationsNonTraitees() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Date dateLimite = cal.getTime();
            reclamationRepository.archiveReclamations(dateLimite);
    }

    public List<Object[]> countReclamationsByMonth() {
        return reclamationRepository.countReclamationsByMonth();
    }

    @Override
    public Reclamation addReclamation(Reclamation rec, Long id) {
        User user = userService.retrieveUserById(id);
        rec.setUser(user);
        return reclamationRepository.save(rec);
    }

    @Override
    public Reclamation updateReclamation(Reclamation rec) {
        return reclamationRepository.save(rec);
    }

    @Override
    public Reclamation retrieveReclamationById(Long idReclamation) {
        return reclamationRepository.findById(idReclamation).orElse(null);
    }

    @Override
    public List<Reclamation> retrieveAllReclamation() {
        return reclamationRepository.findAll();
    }

    @Override
    public List<Reclamation> retrieveAllReclamationByUser(long id) {
        return reclamationRepository.findByUserId(id);
    }

    @Override
    public List<Reclamation> retrieveReclamation(boolean archived) {
        return reclamationRepository.findByArchivedFalse();
    }


    // @Override
    //public void deleteReclamation(Long idReclamation) {
     //   reclamationRepository.deleteById(idReclamation);
    //}


    public void ArchiverReclamation(Long idReclamation) {
        Reclamation reclamation = reclamationRepository.findById(idReclamation)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
        reclamation.setArchived(true);
        reclamationRepository.save(reclamation);
    }



    public List<Reclamation> recupererReclamationsTrieesParDate() {
        return reclamationRepository.findAllByOrderByDateReclamation();
    }






    // @Override
    //public void assignNotificationToReclamation(Long idReclamation, Long idNotification) {
    //Reclamation rec = reclamationRepository.findById(idReclamation).orElse(null);
    //Notification not =notificationRepository.findById(idNotification).orElse(null);
    //rec.setNotifications(not);
    // reclamationRepository.save(rec);
    // }

    //@Override
   // public void assignReclamationToUser(Long idReclamation, Long id) {
      //  Reclamation rec = reclamationRepository.findById(idReclamation).orElse(null);
      //  User user = userRepository.findById(id).orElse(null);
     //   rec.setUser(user);
//  }


}
