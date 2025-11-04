package com.final_exam_springboot.service;

import com.final_exam_springboot.model.dto.request.CreateBuildingRequestDTO;
import com.final_exam_springboot.model.dto.request.UpdateBuildingRequestDTO;
import com.final_exam_springboot.model.dto.response.BuildingResponseDTO;
import com.final_exam_springboot.model.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBuildingService {

    Page<BuildingResponseDTO> findAll(Pageable pageable);
    BuildingResponseDTO save(CreateBuildingRequestDTO createRequestDTO);
    BuildingResponseDTO update(UpdateBuildingRequestDTO updateRequestDTO, Integer id);
    BuildingResponseDTO updateStatus(Integer id, Integer newStatus);
    Page<BuildingResponseDTO> search(String buildingName, Integer status , Pageable pageable);
}
