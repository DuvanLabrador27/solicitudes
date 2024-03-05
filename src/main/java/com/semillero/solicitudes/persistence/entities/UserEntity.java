package com.semillero.solicitudes.persistence.entities;

import com.semillero.solicitudes.persistence.enums.StatusResource;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_user")
    private Long nmIdUser;

    @Basic(optional = false)
    @Column(name = "ds_username")
    private String dsUsername;

    @Basic(optional = false)
    @Column(name = "ds_password")
    private String dsPassword;

    @Basic(optional = false)
    @Column(name = "ds_user_status")
    @Enumerated(value = EnumType.STRING)
    private StatusResource dsUserStatus;

    @Basic(optional = false)
    @Column(name = "fe_user_created" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feUserCreated;

    @Basic(optional = false)
    @Column(name = "fe_user_update" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime feUserUpdate;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = EmployeeEntity.class
    )
    @JoinColumn(name = "nm_id_employee")
    private EmployeeEntity employeeEntity;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = UserRolEntity.class
    )
    @JoinColumn(name = "nm_id_rol")
    private UserRolEntity userRolEntity;

    @OneToMany(
            mappedBy = "userEntity",
            targetEntity = RequestVacationEntity.class
    )
    private Set<RequestVacationEntity> requestVacationEntity;






}
