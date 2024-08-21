package com.esprit.pidev.services.RepasProduitServices;

import com.esprit.pidev.entities.ProduitRepas.CategProduit;
import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.repository.RepasproduitRepository.ProduitRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
public class ProduitService implements IProduit{

    ProduitRepository produitRepository;
    UserRepository userRepository;

    public User getCurrentUserObjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //return new User(user.getUsername(), user.getPassword(), user.getEmail());
        return user;
    }
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //public void checkReclamationsByProduit(Long id) {
    // List<Object[]> result = produitRepository.countReclamationsByProduit();
    //// for (Object[] row : result) {
        //     String nomProduit = (String) row[0];
    // Long nombreReclamations = (Long) row[1];
    // if (nombreReclamations > 10) {
    //  Produit produit = produitRepository.findById(id).orElse(null);
    //  produit.setBloquee(true);
    //  produitRepository.save(produit);
    // }
    //}
    // }


    @Override
    public Produit addProduit(Produit pr) {
        return produitRepository.save(pr);
    }

    @Override
    public Produit updateProduit(Produit pr) throws AccessDeniedException {

        return  produitRepository.save(pr);

    }

    @Override
    public Produit retrieveProduitById(Long id) {

        return produitRepository.findById(id).orElse(null);
    }

    @Override
    public List<Produit> retrieveAllProduit() {

        return produitRepository.findAll();
    }

    @Override
    public void deleteProduit(Produit pr) throws AccessDeniedException {
            produitRepository.delete(pr);
    }

    @Override
    public Set<Produit> getProduitByUserId(long id) {
        Set<Produit> produit = produitRepository.findByUserId(id);
        for (Produit produitItem : produit) {
            if (produitItem.getImageData() != null) {
                try {
                    String imageBase64 = Base64.getEncoder().encodeToString(produitItem.getImageData());
                    produitItem.setImageBase64(imageBase64);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return produit;

    }

    @Override
    public void updateProduitBloqueStatus() {
        produitRepository.blockProduitWithTooManyReclamations();
    }

    @Override
    public List<Produit> getAllProduitAndImage() {
        List<Produit> produit = produitRepository.findAll();
        for (Produit produitItem : produit) {
            if (produitItem.getImageData() != null) {
                try {
                    String imageBase64 = Base64.getEncoder().encodeToString(produitItem.getImageData());
                    produitItem.setImageBase64(imageBase64);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return produit;
    }

    @Override
    public Produit addProduitAndImage(String nom, String description, double prix, String ingredient, CategProduit categProduit, MultipartFile image,long user) throws IOException {
        Produit pt = new Produit();
        pt.setNom(nom);
        pt.setDescription(description);
        pt.setPrix(prix);
        pt.setIngredient(ingredient);
        pt.setCategoriePro(categProduit);
        pt.setUser(userRepository.findById(user).get());
        // pt.setNutrition(nutritionRepository.findById(nutritionId).orElse(null));
        //pt.setUser(userRepository.findById(user).orElse(null));
        byte[] imageData = image.getBytes();
        System.err.println(imageData.toString());
        pt.setImageData(imageData);
        // Save the image file to a folder named 'images' in your project directory
        Path directory = Paths.get("images");
        if (!Files.exists(directory)) {Files.createDirectories(directory);}
        Path imagePath = directory.resolve(UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(image.getOriginalFilename()));
        Files.write(imagePath, imageData);
        produitRepository.save(pt);
        return pt;
    }

    @Override
    public Produit updateProduitAndImage(long id, String nom, String description, double prix, String ingredient, CategProduit categProduit, MultipartFile image,long user) throws IOException {
        Produit pt = new Produit();
        pt.setId(id);
        pt.setNom(nom);
        pt.setDescription(description);
        pt.setPrix(prix);
        pt.setIngredient(ingredient);
        pt.setCategoriePro(categProduit);
        // pt.setNutrition(nutritionRepository.findById(nutritionId).orElse(null));
        pt.setUser(userRepository.findById(user).orElse(null));
        byte[] imageData = image.getBytes();
        System.err.println(imageData.toString());
        pt.setImageData(imageData);
        // Save the image file to a folder named 'images' in your project directory
        Path directory = Paths.get("images");
        if (!Files.exists(directory)) {Files.createDirectories(directory);}
        Path imagePath = directory.resolve(UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(image.getOriginalFilename()));
        Files.write(imagePath, imageData);
        produitRepository.save(pt);
        return pt;
    }



}
