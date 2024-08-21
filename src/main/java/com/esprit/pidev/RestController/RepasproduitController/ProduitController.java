package com.esprit.pidev.RestController.RepasproduitController;

import com.esprit.pidev.entities.ProduitRepas.CategProduit;
import com.esprit.pidev.entities.ProduitRepas.Produit;
import com.esprit.pidev.services.RepasProduitServices.IProduit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class ProduitController {

    IProduit iProduit;

    @PostMapping("/addProduit")
    public Produit addProduit(@RequestBody Produit pr){
        return iProduit.addProduit(pr);

    }
    @PutMapping("/updateProduit")

    public Produit updateProduit(@RequestBody Produit pr) throws AccessDeniedException {
        return iProduit.updateProduit(pr);
    }
    @GetMapping("getProduitById/{id}")
    public Produit retrieveProduitById(@PathVariable("id") Long id){
        return iProduit.retrieveProduitById(id);
    }

    @GetMapping("/getAllProduit")
    public List<Produit> retrieveAllProduit(){
        return iProduit.retrieveAllProduit();
    }
    @DeleteMapping("deleteProduit")

    public void deleteProduit(@RequestBody Produit pr) throws AccessDeniedException {
        iProduit.deleteProduit(pr);
    }
    @GetMapping("/getProduitByUserId/{id}")
    public Set<Produit> getProduitByUserId(@PathVariable("id") long id) {
        return iProduit.getProduitByUserId(id);
    }

    @PutMapping("/checkReclamationsByProduit")
    void checkReclamationsByProduit(){
        iProduit.updateProduitBloqueStatus();
    }

    @PostMapping("/addProduitWithImg")
    public Produit addRepasAndImage(@RequestParam("nom")String nom, @RequestParam("description") String description, @RequestParam("prix") double prix, @RequestParam("ingredient") String ingredient, @RequestParam("categProduit") CategProduit categProduit, @RequestParam("image") MultipartFile image,@RequestParam("user") long user) throws IOException {
        return iProduit.addProduitAndImage(nom,  description,  prix,  ingredient, categProduit,image,user);
    }

    @PutMapping("/updateProduitWithImg")
    public Produit updateRepasAndImage(@RequestParam("id")long id,@RequestParam("nom")String nom, @RequestParam("description") String description,@RequestParam("prix") double prix,@RequestParam("ingredient") String ingredient,@RequestParam("categProduit") CategProduit categProduit, @RequestParam("image") MultipartFile image,@RequestParam("user") long user) throws IOException
    {
        return iProduit.updateProduitAndImage(id,nom,  description,  prix,  ingredient, categProduit,image,user);
    }
    @GetMapping("/getAllProduitWithImage")
    public List<Produit> retrieveAllProduitWithImage(){
        return  iProduit.getAllProduitAndImage();
    }
}
