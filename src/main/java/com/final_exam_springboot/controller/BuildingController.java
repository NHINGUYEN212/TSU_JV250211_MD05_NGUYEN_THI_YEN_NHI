package com.final_exam_springboot.controller;

import com.final_exam_springboot.model.dto.ResponseWrapper;
import com.final_exam_springboot.model.dto.request.CreateBuildingRequestDTO;
import com.final_exam_springboot.model.dto.request.UpdateBuildingRequestDTO;
import com.final_exam_springboot.model.dto.response.BuildingResponseDTO;
import com.final_exam_springboot.service.IBuildingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/buildings")
public class BuildingController {
    private final IBuildingService buildingService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<BuildingResponseDTO>>> findAll(Pageable pageable) {
        Page<BuildingResponseDTO> responseDTOS = buildingService.findAll(pageable);
        return new ResponseEntity<>(
                ResponseWrapper.<Page<BuildingResponseDTO>>builder()
                        .success(true)
                        .message("Lay thanh cong danh sach toa nha")
                        .data(responseDTOS)
                        .httpStatus(HttpStatus.OK.value())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<BuildingResponseDTO>> create(
            @Valid @ModelAttribute CreateBuildingRequestDTO createRequestDTO) {
        BuildingResponseDTO responseDTO = buildingService.save(createRequestDTO);
        return new ResponseEntity<>(
                ResponseWrapper.<BuildingResponseDTO>builder()
                        .success(true)
                        .message("Them moi toa nha thanh cong")
                        .data(responseDTO)
                        .httpStatus(HttpStatus.CREATED.value())
                        .build(), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<BuildingResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @ModelAttribute UpdateBuildingRequestDTO updateRequestDTO
    ) {
        BuildingResponseDTO responseDTO = buildingService.update(updateRequestDTO, id);
        return new ResponseEntity<>(
                ResponseWrapper.<BuildingResponseDTO>builder()
                        .success(true)
                        .message("Cap nhat toa nha thanh cong")
                        .data(responseDTO)
                        .httpStatus(HttpStatus.OK.value())
                        .build(), HttpStatus.OK
        );
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ResponseWrapper<BuildingResponseDTO>> updateStatus(
            @PathVariable Integer id,
            @RequestParam Integer status
    ) {
        BuildingResponseDTO responseDTO = buildingService.updateStatus(id, status);
        return new ResponseEntity<>(
                ResponseWrapper.<BuildingResponseDTO>builder()
                        .success(true)
                        .message("Cap nhat trang thai thanh cong")
                        .data(responseDTO)
                        .httpStatus(HttpStatus.OK.value())
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Page<BuildingResponseDTO>>> search(
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) Integer status,
            Pageable pageable
    ) {
        Page<BuildingResponseDTO> responseDTOS = buildingService.search(buildingName, status, pageable);
        return new ResponseEntity<>(
                ResponseWrapper.<Page<BuildingResponseDTO>>builder()
                        .success(true)
                        .message("Tim kiem toa nha thanh cong")
                        .data(responseDTOS)
                        .httpStatus(HttpStatus.OK.value())
                        .build(), HttpStatus.OK
        );
    }
}
