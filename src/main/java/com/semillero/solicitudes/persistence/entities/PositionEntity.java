package com.semillero.solicitudes.persistence.entities;

import com.semillero.solicitudes.persistence.enums.PositionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "position")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_position")
    private Long nmIdPosition;

    @Basic(optional = false)
    @Column(name = "ds_position_name")
    private String dsPositionName;

    @Basic(optional = false)
    @Column(name = "ds_position_description")
    private String dsPositionDescription;

    @Basic(optional = false)
    @Column(name = "ds_position_status")
    private PositionStatus dsPositionStatus;

    @Basic(optional = false)
    @Column(name = "fe_position_creation" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fePositionCreation;


    @OneToMany(mappedBy = "positionEntity",
            targetEntity = EmployeeEntity.class)
    private Set<EmployeeEntity> employeeEntity;
}
