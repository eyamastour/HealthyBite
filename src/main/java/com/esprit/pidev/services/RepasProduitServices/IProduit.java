package com.esprit.pidev.services.RepasProduitServices;

import com.esprit.pidev.entities.ProduitRepas.CategProduit;
import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

public interface IProduit {
    Produit addProduit(Produit pr);
    Produit updateProduit(Produit pr) throws AccessDeniedException;
    Produit retrieveProduitById(Long id);
    List<Produit> retrieveAllProduit();
    void deleteProduit(Produit pr) throws AccessDeniedException;

    public Set<Produit> getProduitByUserId(long id);
    //void checkReclamationsByProduit(Long id);
    void updateProduitBloqueStatus();

    public List<Produit> getAllProduitAndImage();
    public Produit addProduitAndImage(String nom, String description, double prix, String ingredient, CategProduit categProduit, MultipartFile image,long user) throws IOException;
    public Produit updateProduitAndImage(long id, String nom, String description, double prix, String ingredient, CategProduit categProduit, MultipartFile image,long user) throws IOException;
}
