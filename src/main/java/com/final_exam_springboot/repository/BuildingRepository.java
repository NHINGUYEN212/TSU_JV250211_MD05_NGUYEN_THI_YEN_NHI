package com.final_exam_springboot.repository;

import com.final_exam_springboot.model.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Integer> {
    boolean existsByBuildingName(String buildingName);
    boolean existsByBuildingNameAndIdNot(String buildingName, Integer id);

    Page<Building> findByBuildingNameContainingIgnoreCase(String  buildingName, Pageable pageable);
    Page<Building> findByStatus(Integer status, Pageable pageable);
    Page<Building> findByBuildingNameContainingIgnoreCaseAndStatus(String buildingName, Integer status, Pageable pageable);
}
