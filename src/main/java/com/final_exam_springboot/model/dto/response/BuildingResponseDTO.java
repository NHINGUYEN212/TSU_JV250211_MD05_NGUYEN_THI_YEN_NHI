package com.final_exam_springboot.model.dto.response;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BuildingResponseDTO {
    private Integer id;
    private String buildingName;
    private Double buildingArea;
    private String areaUnit;
    private LocalDate startDate;
    private Integer time;
    private String timeUnit;
    private String design;
    private String content;
    private String status;
}
