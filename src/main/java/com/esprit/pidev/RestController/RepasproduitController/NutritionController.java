package com.esprit.pidev.RestController.RepasproduitController;

import com.esprit.pidev.entities.ProduitRepas.Nutrition;
import com.esprit.pidev.services.RepasProduitServices.INutrition;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Data
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/test")
public class NutritionController {
    INutrition iNutrition;

    @PostMapping("/addNutrition")
    public Nutrition addNutrition(@RequestBody Nutrition nut){
        return iNutrition.addNutrition(nut);

    }
    @PutMapping("/updateNutrition")
    public Nutrition updateNutrition(@RequestBody Nutrition nut){
        return iNutrition.updateNutrition(nut);
    }
    @GetMapping("getNutritionById/{id}")
    public Nutrition retrieveNutritionById(@PathVariable("id") Long id){
        return iNutrition.retrieveNutritionById(id);
    }

    @GetMapping("/getAllNutrition")
    public List<Nutrition> retrieveAllNutrition(){
        return iNutrition.retrieveAllNutrition();
    }
    @DeleteMapping("deleteNutrition/{id}")
    public void deleteNutrition(@PathVariable("id") Long id){
        iNutrition.deleteNutrition(id);
    }



}
