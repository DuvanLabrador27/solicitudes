package com.semillero.solicitudes.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "request_vacation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestVacationEntity{
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


}
