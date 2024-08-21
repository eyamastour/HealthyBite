package com.esprit.pidev.RestController.ReclamationEtReponseController;

import com.esprit.pidev.entities.ReclamationEtReponse.Notification;
import com.esprit.pidev.services.ReclamationEtReponseService.INotification;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/api/test")
public class NotificationController {
    INotification iNotification;
    @PostMapping("/addNotification")
    public Notification addNotification(@RequestBody Notification not){
        return iNotification.addNotification(not);
    }
    @PutMapping("/updateNotification")
    public Notification updateNotification(@RequestBody Notification not){
        return iNotification.updateNotification(not);
    }
    @GetMapping("/retrieveNotificationById/{idNotification}")
    public Notification retrieveNotificationById(@PathVariable("idNotification") Long idNotification){
        return iNotification.retrieveNotificationById(idNotification);
    }
    @GetMapping("/retrieveAllNotification")
    public List<Notification> retrieveAllNotification(){
        return iNotification.retrieveAllNotification();
    }
    @DeleteMapping("/deleteNotification/{idNotification}")
    public void deleteNotification(@PathVariable("idNotification") Long idNotification){
        iNotification.deleteNotification(idNotification);
    }

}
