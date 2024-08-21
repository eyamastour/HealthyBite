package com.esprit.pidev.RestController.RepasproduitController;

import com.esprit.pidev.entities.ConseilRecette.TypeActivite;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.ProduitRepas.CategRepas;
import com.esprit.pidev.entities.ProduitRepas.ObjectifType;
import com.esprit.pidev.entities.ProduitRepas.Repas;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.security.services.IUser;
import com.esprit.pidev.services.RepasProduitServices.IRepas;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@AllArgsConstructor
@Data
@RequestMapping("/api/test")
public class RepasController {
    @Autowired
    IRepas iRepas;
    @Autowired
    IUser iUser;

    @PostMapping("/addRepas")
    public Repas addRepas(@RequestBody Repas rep){
        return iRepas.addRepas(rep);

    }
    @PutMapping("/updateRepas")
    public Repas updateRepas(@RequestBody Repas rep) throws AccessDeniedException {
        return iRepas.updateRepas(rep);
    }
    @PostMapping("/addRepasWithImg")
    public Repas addRepasAndImage(@RequestParam("nom")String nom, @RequestParam("description") String description,@RequestParam("user") long user,@RequestParam("prix") double prix,@RequestParam("ingredient") String ingredient,@RequestParam("allergene") String allergene,@RequestParam("objectifType") ObjectifType objectifType,@RequestParam("categRepas") CategRepas categRepas, @RequestParam("image") MultipartFile image) throws IOException {
        return iRepas.addRepasAndImage(nom,  description,  prix,  ingredient,  allergene,  objectifType,
        categRepas,user, image);
    }
    @PutMapping("/updateRepasWithImg")
    public Repas updateRepasAndImage(@RequestParam("id") long id, @RequestParam("nom")String nom, @RequestParam("description") String description,@RequestParam("user") long user,@RequestParam("prix") double prix,@RequestParam("ingredient") String ingredient,@RequestParam("allergene") String allergene,@RequestParam("objectifType") ObjectifType objectifType,@RequestParam("categRepas") CategRepas categRepas, @RequestParam("image") MultipartFile image) throws IOException {
        return iRepas.updateRepasAndImage( id, nom,  description,  prix,  ingredient,  allergene,  objectifType,  categRepas,  image, user);
    }

    @GetMapping("getRepasById/{id}")
    public Repas retrieveRepasById(@PathVariable("id") long id){
        return iRepas.retrieveRepasById(id);
    }

    @GetMapping("/getAllRepas")
    public List<Repas> retrieveAllRepas(){
        return iRepas.retrieveAllRepas();
    }

    @GetMapping("/getAllRepasAndImage")
    public List<Repas> retrieveAllRepasAndImage(){
        return iRepas.getAllRepasAndImage();
    }


    @DeleteMapping("/deleteRepas")
    public void deleteRepas(@RequestBody Repas rep)  {
        iRepas.deleteRepas(rep);

    }


    @GetMapping("/getRepasByUserId/{id}")
    public Set<Repas> getRepasByUserId(@PathVariable("id") Long id) {
        return iRepas.getRepasByUserId(id);

    }


    @PostMapping("/totalCalories")
    public int calculerCaloriesTotales(@RequestBody List<Repas> repasChoisis) {
        return iRepas.calculerCaloriesTotales(repasChoisis);
    }
    @PutMapping("/checkReclamationsByRepas")
    public void checkReclamationsByRepas(){
        iRepas.updateRepasBloqueStatus();
    }

    @PostMapping ("/maxCalories")
    public double calculerMaxCalories(@RequestBody User user) {
        return iRepas.calculerMaxCalories(user);
    }
   @GetMapping("/searchRepas")
    public List<Repas> searchRepasByNom(@RequestParam("nom") String nom) {
        return iRepas.rechercherRepasParNom(nom);
    }



    @GetMapping("/proposer")
    public ResponseEntity<List<Repas>> proposerRepasSelonObjectifEtActivite()
    {
        List<Repas> repasProposes = iRepas.proposerRepasSelonObjectifEtActivite();
        return ResponseEntity.ok(repasProposes);
    }
/*
    @PostMapping("/checkNewRepas")
    public ResponseEntity<String> checkNewRepas() {
        iRepas.checkNewRepas();
        return ResponseEntity.ok("Vérification des nouveaux repas en cours !");
    }*/


}
