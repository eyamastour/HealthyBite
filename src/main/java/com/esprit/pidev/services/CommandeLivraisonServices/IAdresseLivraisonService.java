package com.esprit.pidev.services.CommandeLivraisonServices;

import com.esprit.pidev.entities.CommandeLivraison.AdresseLivraison;
import com.esprit.pidev.entities.ProduitRepas.Nutrition;

import java.util.List;

public interface IAdresseLivraisonService {

    AdresseLivraison addAdresseLivraison(AdresseLivraison adresseLivraison);
    AdresseLivraison updateAdresseLivraison(AdresseLivraison adresseLivraison);
    void deleteAdresseLivraisonById(Long id);
    AdresseLivraison getAdresseLivraisonById(Long id);
    List<AdresseLivraison> getAllAdressesLivraison();
    boolean existAdresseLivraison(Long idAdr);
}
