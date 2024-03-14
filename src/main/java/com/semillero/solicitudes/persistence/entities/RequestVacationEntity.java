package com.semillero.solicitudes.persistence.entities;

import com.semillero.solicitudes.persistence.enums.StatusRequestVacation;
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

    @Column(name = "name_request")
    private String nameRequest;

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

    @Column(name = "ds_status", length = 10, columnDefinition = "VARCHAR(10) DEFAULT 'PENDING'")
    @Enumerated(value = EnumType.STRING)
    private StatusRequestVacation dsStatus = StatusRequestVacation.PENDING;

    @Basic(optional = false)
    @Column(name = "fe_request_creation",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feRequestCreation = LocalDateTime.now();

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = UserEntity.class,
            //Mientras
            optional = true
    )
    @JoinColumn(name = "nm_id_user")
    private UserEntity userEntity;

}
