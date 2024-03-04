package com.semillero.solicitudes.persistence.entities;

import com.semillero.solicitudes.utils.StatusRequestVacation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_vacation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestVacationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_request")
    private Long nmIdRequest;

    @Basic(optional = false)
    @Column(name = "name_request")
    private String name_request;

    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "nm_number_of_days_requested")
    private Integer nmNumberOfDaysRequested;

    @Basic(optional = false)
    @Column(name = "fe_start_date")
    private LocalDate feStartDate;

    @Basic(optional = false)
    @Column(name = "fe_end_date")
    private LocalDate feEndDate;

    @Basic(optional = false)
    @Column(name = "fe_reinstatement_date")
    private LocalDate feReinstatementDate;

    @Basic(optional = false)
    @Column(name = "ds_status", length = 10)
    @Enumerated(value = EnumType.STRING)
    private StatusRequestVacation dsStatus;

    @Basic(optional = false)
    @Column(name = "ds_comment_review")
    private String dsCommentReview;

    @Basic(optional = false)
    @Column(name = "fe_request_creation" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feRequestCreation;

    @Basic(optional = false)
    @Column(name = "fe_request_update" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime feRequestUpdate;












}
