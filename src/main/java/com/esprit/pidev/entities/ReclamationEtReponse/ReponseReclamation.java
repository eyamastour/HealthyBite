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
public class ReponseReclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReponseReclamation;

    @Temporal(TemporalType.DATE )
    private Date dateReponseReclamation;
    private String textReponseReclamation;

    @OneToOne(mappedBy = "reponseReclamation")
    @JsonIgnore
    private  Reclamation reclamation;

}
