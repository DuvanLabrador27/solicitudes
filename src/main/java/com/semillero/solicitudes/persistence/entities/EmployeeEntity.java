package com.semillero.solicitudes.persistence.entities;

import com.semillero.solicitudes.persistence.enums.DocumentTypes;
import com.semillero.solicitudes.persistence.enums.StatusResource;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_employee")
    private Long nmIdEmployee;

    @Basic(optional = false)
    @Column(name = "ds_document")
    private String dsDocument;

    @Basic(optional = false)
    @Column(name = "ds_document_type")
    @Enumerated(value = EnumType.STRING)
    private DocumentTypes dsDocumentType;

    @Basic(optional = false)
    @Column(name = "ds_name")
    private String dsName;

    @Basic(optional = false)
    @Column(name = "ds_lastname")
    private String dsLastname;

    @Basic(optional = false)
    @Column(name = "ds_phoneNumber")
    private String dsPhoneNumber;

    @Basic(optional = false)
    @Column(name = "ds_address")
    private String dsAddress;


    @Column(name = "fe_hire_date")
    private LocalDate feHireDate;


    @Column(name = "fe_departure_date")
    private LocalDate feDepartureDate;

    @Basic(optional = false)
    @Column(name = "ds_type_of_contract", length = 50)
    @Enumerated(value = EnumType.STRING)
    private TypeOfContract dsTypeOfContract;

    @Basic(optional = false)
    @Column(name = "ds_employee_status", length = 20)
    @Enumerated(value = EnumType.STRING)
    private StatusResource dsEmployeeStatus;

    @OneToMany(mappedBy = "employeeEntity",
            targetEntity = UserEntity.class)
    private List<UserEntity> userEntity;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employeeSupervisor"
    )
    private List<EmployeeEntity> employeeEntity;

    @ManyToOne(optional = true)
    @JoinColumn(name = "nm_id_supervisor")
    private EmployeeEntity employeeSupervisor;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = PositionEntity.class,
            optional = true
    )
    @JoinColumn(name = "nm_id_position")
    private PositionEntity positionEntity;


}
