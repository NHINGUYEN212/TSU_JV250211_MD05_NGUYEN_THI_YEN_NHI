package com.final_exam_springboot.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateBuildingRequestDTO {

    private String buildingName;


    private Double buildingArea;


    private String areaUnit;


    private LocalDate startDate;


    @Min(value = 1, message = "Thoi gian phai lon hon 0")
    private Integer time;


    private String timeUnit;

    private MultipartFile design;

    private String content;

}
