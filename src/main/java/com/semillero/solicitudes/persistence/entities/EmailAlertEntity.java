package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_alert")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_email_alert")
    private Long nmIdEmailAlert;

    @Basic(optional = false)
    @Column(name = "ds_subject")
    private String dsSubject;

    @Basic(optional = false)
    @Column(name = "ds_user_recipient")
    private String dsUserRecipient;

    @Basic(optional = false)
    @Column(name = "ds_email_body")
    private String dsEmailBody;

    @Basic(optional = false)
    @Column(name = "ds_alert_status")
    private String dsAlertStatus;

    @Basic(optional = false)
    @Column(name = "fe_email_alert_creation" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feEmailAlertCreation;

    @Basic(optional = false)
    @Column(name = "fe_email_alert_update" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime feEmailAlertUpdate;

}
