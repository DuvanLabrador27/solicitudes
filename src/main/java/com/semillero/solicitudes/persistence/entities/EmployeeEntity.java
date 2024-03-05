package com.semillero.solicitudes.persistence.entities;

import com.semillero.solicitudes.persistence.enums.DocumentTypes;
import com.semillero.solicitudes.persistence.enums.StatusResource;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Basic(optional = false)
    @Column(name = "fe_hire_date")
    private LocalDate feHireDate;

    @Basic(optional = false)
    @Column(name = "fe_departure_date")
    private String feDepartureDate;

    @Basic(optional = false)
    @Column(name = "ds_type_of_contract")
    @Enumerated(value = EnumType.STRING)
    private TypeOfContract dsTypeOfContract;

    @Basic(optional = false)
    @Column(name = "ds_employee_status")
    @Enumerated(value = EnumType.STRING)
    private StatusResource dsEmployeeStatus;





}
