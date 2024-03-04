package com.semillero.solicitudes.persistence.repository;


import com.semillero.solicitudes.persistence.entity.RequestVacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestVacationRepository extends JpaRepository<RequestVacationEntity, Long> {
}
