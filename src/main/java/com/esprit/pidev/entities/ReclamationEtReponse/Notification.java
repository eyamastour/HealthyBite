package com.esprit.pidev.entities.ReclamationEtReponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;
    @Temporal(TemporalType.DATE )
    private Date dateNotification;
    private String textNotification;

    @OneToOne(mappedBy = "notifications")
    @JsonIgnore
    private  Reclamation reclamation;

}
