package com.esprit.pidev.services.ReclamationEtReponseService;

import com.esprit.pidev.entities.ReclamationEtReponse.Notification;

import com.esprit.pidev.repository.ReclamationEtReponseRepository.NotificationRepository;
import com.esprit.pidev.repository.ReclamationEtReponseRepository.ReclamationRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@AllArgsConstructor
public class NotificationService implements INotification{
    NotificationRepository notificationRepository;
    ReclamationRepository reclamationRepository;
    UserRepository userRepository;
    @Override
    public Notification addNotification(Notification not) {
        return notificationRepository.save(not);
    }

    @Override
    public Notification updateNotification(Notification not) {
        return notificationRepository.save(not);
    }

    @Override
    public Notification retrieveNotificationById(Long idNotification) {
        return notificationRepository.findById(idNotification).orElse(null);
    }

    @Override
    public List<Notification> retrieveAllNotification() {
        return notificationRepository.findAll();
    }

    @Override
    public void deleteNotification(Long idNotification) {
        notificationRepository.deleteById(idNotification);
    }

}
