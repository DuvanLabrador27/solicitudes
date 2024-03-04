package com.semillero.solicitudes.persistence.repositories;


import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestVacationRepository extends JpaRepository<RequestVacationEntity, Long> {
}
