package com.esprit.pidev.RestController.ConseilRecetteController;


import com.esprit.pidev.entities.ConseilRecette.Conseil;
import com.esprit.pidev.entities.ConseilRecette.Objectif;
import com.esprit.pidev.security.services.UserService;
import com.esprit.pidev.services.*;

import com.esprit.pidev.services.ConseilRecetteServices.IConseilService;
import com.esprit.pidev.services.ConseilRecetteServices.IObjectifService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/conseil")
@AllArgsConstructor
public class ConseilController {


    private UserService userService;

    private IConseilService conseilService;

    private IObjectifService objectifService;


    @PostMapping("/")
    public ResponseEntity<Conseil> addConseil(@RequestBody Conseil conseil) {

        Conseil saved = conseilService.addConseil(conseil);
        return new ResponseEntity<Conseil>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conseil> updateConseil(@PathVariable("id") Long id, @RequestBody Conseil conseil) {
        Conseil updated = conseilService.updateConseil(id, conseil);
        return new ResponseEntity<Conseil>(updated, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Conseil> findConseilById(@PathVariable(value = "id") Long id) {
        Optional<Conseil> conseil = conseilService.retrieveConseilById(id);
        return new ResponseEntity<Conseil>(conseil.get(), HttpStatus.FOUND);
    }


    @GetMapping(value = "/")
    public ResponseEntity<Collection<Conseil>> getAllConseils() {
        Collection<Conseil> conseils = conseilService.retrieveAllConseil();
        return new ResponseEntity<Collection<Conseil>>(conseils, HttpStatus.FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteConseil(@PathVariable(value = "id", required = true) Long id) {
        conseilService.deleteConseil(id);
        return new ResponseEntity<Void>(HttpStatus.GONE);
    }


    //    add conseil to objectif
    @PostMapping("/objectif/{objectifId}/conseils")
    public ResponseEntity<Conseil> addConseilToObjectif(
            @PathVariable("objectifId") Long objectifId, @RequestBody Conseil conseil) {

        Conseil c = conseilService.addConseil(conseil, objectifId);
        return new ResponseEntity<Conseil>(c, HttpStatus.CREATED);
    }


    //    show conseil per objectif
    @GetMapping("/objectif/{objectifId}/conseils")
    public List<Conseil> getConseilPerObjectif(@PathVariable("objectifId") Long objectifId) {
        Objectif o = objectifService.retrieveObjectifById(objectifId).orElse(null);
        return o.getConseils();
    }


    @GetMapping("/user/{userId}/recommend")
    public ResponseEntity<List<Conseil>> conseilRecommendations(@PathVariable("userId") Long userId) {
        List<Conseil> list = conseilService.recommend(userId);
        return new ResponseEntity<List<Conseil>>(list, HttpStatus.CREATED);
    }


}
