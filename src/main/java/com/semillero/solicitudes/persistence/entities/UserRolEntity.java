package com.semillero.solicitudes.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user_rol")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_user_rol")
    private Long nmIdUserRol;

    @Basic(optional = false)
    @Column(name = "ds_rol_name")
    private String dsRolName;

    @Basic(optional = false)
    @Column(name = "ds_user_rol_creation" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feUserRolCreation;

    @Basic(optional = false)
    @Column(name = "ds_user_rol_update" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime feUserRolUpdate;

    @OneToMany(mappedBy = "userRolEntity",
            targetEntity = UserEntity.class)
    private Set<UserEntity> userEntity;
}
