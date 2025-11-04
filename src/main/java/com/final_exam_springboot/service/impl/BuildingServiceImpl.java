package com.final_exam_springboot.service.impl;

import com.final_exam_springboot.exception.HttpConflict;
import com.final_exam_springboot.exception.HttpNotFound;
import com.final_exam_springboot.model.dto.request.CreateBuildingRequestDTO;
import com.final_exam_springboot.model.dto.request.UpdateBuildingRequestDTO;
import com.final_exam_springboot.model.dto.response.BuildingResponseDTO;
import com.final_exam_springboot.model.entity.Building;
import com.final_exam_springboot.repository.BuildingRepository;
import com.final_exam_springboot.service.IBuildingService;
import com.final_exam_springboot.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements IBuildingService {
    private final BuildingRepository buildingRepository;
    private final UploadService uploadService;

    private BuildingResponseDTO convertToDTO(Building building) {
        String statusText;
        switch (building.getStatus()) {
            case 0:
                statusText = "Da huy bo";
                break;
            case 1:
                statusText = "Dang thuc hien";
                break;
            case 2:
                statusText = "Da hoan thanh";
                break;
            default:
                statusText = "Khong xac dinh duoc trang thai";
        }
        return BuildingResponseDTO.builder()
                .id(building.getId())
                .buildingName(building.getBuildingName())
                .buildingArea(building.getBuildingArea())
                .areaUnit(building.getAreaUnit())
                .startDate(building.getStartDate())
                .time(building.getTime())
                .timeUnit(building.getTimeUnit())
                .status(statusText)
                .design(building.getDesign())
                .content(building.getContent())
                .build();
    }

    @Override
    public Page<BuildingResponseDTO> findAll(Pageable pageable) {
        Page<Building> buildingPage = buildingRepository.findAll(pageable);
        return buildingPage.map(this::convertToDTO);
    }

    @Override
    public BuildingResponseDTO save(CreateBuildingRequestDTO createRequestDTO) {
        if (buildingRepository.existsByBuildingName(createRequestDTO.getBuildingName())) {
            throw new HttpConflict("Ten toa nha da ton tai");
        }
        String designUrl = uploadService.uploadFile(createRequestDTO.getDesign());
        if (designUrl == null) {
            throw new HttpConflict("Loi khi upload anh");
        }
        Building building = Building.builder()
                .buildingName(createRequestDTO.getBuildingName())
                .buildingArea(createRequestDTO.getBuildingArea())
                .areaUnit(createRequestDTO.getAreaUnit())
                .startDate(createRequestDTO.getStartDate())
                .time(createRequestDTO.getTime())
                .timeUnit(createRequestDTO.getTimeUnit())
                .design(designUrl)
                .content(createRequestDTO.getContent())
                .status(1)
                .build();
        Building buildingNew = buildingRepository.save(building);
        return convertToDTO(buildingNew);
    }

    @Override
    public BuildingResponseDTO update(UpdateBuildingRequestDTO updateRequestDTO, Integer id) {
        Building buildingToUpdate = buildingRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Khong tim thay toa nha"));

        if (updateRequestDTO.getBuildingName() != null && !updateRequestDTO.getBuildingName().isBlank()) {
            if (buildingRepository.existsByBuildingNameAndIdNot(updateRequestDTO.getBuildingName(), id)) {
                throw new HttpConflict("Ten toa nha da ton tai");
            }
            buildingToUpdate.setBuildingName(updateRequestDTO.getBuildingName());
        }
        if (updateRequestDTO.getBuildingArea() != null) {
            buildingToUpdate.setBuildingArea(updateRequestDTO.getBuildingArea());
        }
        if (updateRequestDTO.getAreaUnit() != null && !updateRequestDTO.getAreaUnit().isBlank()) {
            buildingToUpdate.setAreaUnit(updateRequestDTO.getAreaUnit());
        }
        if (updateRequestDTO.getStartDate() != null) {
            buildingToUpdate.setStartDate(updateRequestDTO.getStartDate());
        }
        if (updateRequestDTO.getTime() != null) {
            buildingToUpdate.setTime(updateRequestDTO.getTime());
        }
        if (updateRequestDTO.getTimeUnit() != null && !updateRequestDTO.getTimeUnit().isBlank()) {
            buildingToUpdate.setTimeUnit(updateRequestDTO.getTimeUnit());
        }
        if (updateRequestDTO.getContent() != null && !updateRequestDTO.getContent().isBlank()) {
            buildingToUpdate.setContent(updateRequestDTO.getContent());
        }
        if (updateRequestDTO.getDesign() != null && !updateRequestDTO.getDesign().isEmpty()) {
            String newDesignUrl = uploadService.uploadFile(updateRequestDTO.getDesign());
            buildingToUpdate.setDesign(newDesignUrl);
        }
        Building buildingNew = buildingRepository.save(buildingToUpdate);
        return convertToDTO(buildingNew);

    }

    @Override
    public BuildingResponseDTO updateStatus(Integer id, Integer newStatus) {
        if(newStatus == null || newStatus < 0 || newStatus > 2) {
            throw new HttpConflict("Trang thai khong hop le, chi co the la 0, 1, 2");
        }
        Building building =buildingRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Khong tim thay toa nha"));
        if (building.getStatus() ==2) {
            throw new HttpConflict("Toa nha o trang thai da hoan thanh nen khong the thay doi trang thai");
        }
        if (building.getStatus().equals(newStatus)) {
            return convertToDTO(building);
        }
        building.setStatus(newStatus);
        Building buildingUpdated = buildingRepository.save(building);
        return convertToDTO(buildingUpdated);
    }

    @Override
    public Page<BuildingResponseDTO> search(String buildingName, Integer status, Pageable pageable) {
        Page<Building> buildingPage;
        boolean hasName = (buildingName != null && !buildingName.isBlank());
        boolean hasStatus = (status != null);
        if (hasName && hasStatus) {
            buildingPage = buildingRepository.findByBuildingNameContainingIgnoreCaseAndStatus(buildingName, status, pageable);

        } else if (hasName) {
            buildingPage = buildingRepository.findByBuildingNameContainingIgnoreCase(buildingName, pageable);
        } else if (hasStatus) {
            buildingPage = buildingRepository.findByStatus(status, pageable);
        } else {
            buildingPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return buildingPage.map(this::convertToDTO);
    }
}
