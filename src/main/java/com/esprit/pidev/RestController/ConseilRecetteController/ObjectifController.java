package com.esprit.pidev.RestController.ConseilRecetteController;


import com.esprit.pidev.entities.ConseilRecette.Objectif;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.exceptions.RecetteNotFoundException;

import com.esprit.pidev.security.services.UserService;
import com.esprit.pidev.services.ConseilRecetteServices.IObjectifService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/objectif")
public class ObjectifController {

    @Autowired

    private IObjectifService objectifService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Objectif> addbjectif(@RequestBody Objectif objectif) {

        Objectif saved = objectifService.addObjectif(objectif);
        return new ResponseEntity<Objectif>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objectif> updatebjectif(@PathVariable("id") Long id, @RequestBody Objectif objectif) {
        Objectif updated = objectifService.updateObjectif(id, objectif);
        return new ResponseEntity<Objectif>(updated, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Objectif> findbjectifById(@PathVariable(value = "id") Long id) {
        Optional<Objectif> objectif = objectifService.retrieveObjectifById(id);
        return new ResponseEntity<Objectif>(objectif.get(), HttpStatus.FOUND);
    }


    @GetMapping(value = "/")
    public ResponseEntity<Collection<Objectif>> getAllbjectifs() {
        Collection<Objectif> objectifs = objectifService.retrieveAllObjectif();
        return new ResponseEntity<Collection<Objectif>>(objectifs, HttpStatus.FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletebjectif(@PathVariable(value = "id", required = true) Long id) {
        objectifService.deleteObjectif(id);
        return new ResponseEntity<Void>(HttpStatus.GONE);
    }

    @PostMapping("/userObjectifs/{userId}")
    public ResponseEntity<Objectif> addObjectifToUser(
            @PathVariable("userId") Long userId, @RequestBody Objectif objectif) {

        Objectif saved = objectifService.addObjectif(objectif, userId);
        return new ResponseEntity<Objectif>(objectifService.addObjectif(objectif, userId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/objectifs")
    public List<Objectif> getObjectifsPerUser(@PathVariable("userId") Long userId) {
        User user = userService.retrieveUserById(userId);
        if (user == null) {
            throw new RecetteNotFoundException("User not Found");
        }
        return user.getObjectifs();
    }

}
