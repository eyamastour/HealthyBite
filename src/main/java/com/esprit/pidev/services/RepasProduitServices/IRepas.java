package com.esprit.pidev.services.RepasProduitServices;

import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.ProduitRepas.CategRepas;
import com.esprit.pidev.entities.ProduitRepas.ObjectifType;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import com.esprit.pidev.entities.UserRole.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

public interface IRepas {
    Repas addRepas(Repas rep);
    Repas updateRepas(Repas rep) throws AccessDeniedException;
    Repas retrieveRepasById(Long id);
    List<Repas> retrieveAllRepas();
    void deleteRepas(Repas rep) ;
     Set<Repas> getRepasByUserId(long  id);

    void updateRepasBloqueStatus( );
    int calculerCaloriesTotales(List<Repas> repasChoisis);

    long calculerMaxCalories(User user);

    List<Repas> proposerRepasSelonObjectifEtActivite();

     List<Repas> rechercherRepasParNom(String nom);

    Repas addRepasAndImage(String nom, String description, double prix, String ingredient, String allergene, ObjectifType objectifType, CategRepas categRepas,long user,  MultipartFile image)throws IOException;

    Repas updateRepasAndImage(long id,String nom, String description, double prix, String ingredient, String allergene, ObjectifType objectifType, CategRepas categRepas, MultipartFile image,long user) throws IOException;
    public List<Repas> getAllRepasAndImage();
}
