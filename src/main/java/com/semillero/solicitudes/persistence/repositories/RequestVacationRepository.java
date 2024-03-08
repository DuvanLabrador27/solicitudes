package com.semillero.solicitudes.persistence.repositories;


import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RequestVacationRepository extends JpaRepository<RequestVacationEntity, Long> {
    @Query(value = "SELECT * FROM request_vacation rv  WHERE rv.nm_id_request = ?1 AND rv.nm_id_user = ?2 ", nativeQuery = true)
    List<RequestVacationEntity> findByNmIdRequestAndNmIdUser(Long nmIdRequest, Long nmIdUser);
    @Query(value = "SELECT * FROM request_vacation rv WHERE rv.nm_id_user = ?1 ORDER BY rv.fe_request_creation DESC ", nativeQuery = true)
    List<RequestVacationEntity> findAllRequestByUser(Long userId);

}
