package com.semillero.solicitudes.persistence.entities;

import com.semillero.solicitudes.utils.StatusUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private StatusUser dsUserStatus;

    @Basic(optional = false)
    @Column(name = "fe_user_created" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime feUserCreated;

    @Basic(optional = false)
    @Column(name = "fe_user_update" ,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime feUserUpdate;








}
