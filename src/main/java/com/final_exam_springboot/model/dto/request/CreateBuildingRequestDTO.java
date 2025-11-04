package com.final_exam_springboot.model.dto.request;

import com.final_exam_springboot.validate.FileNotBlank;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateBuildingRequestDTO {
    @NotBlank(message = "Ten toa nha khong duoc de trong")
    private String buildingName;

    @NotNull(message = "Dien tich khong duoc de trong")
    private Double buildingArea;

    @NotBlank(message = "Don vi dien tich khong duoc de trong")
    private String areaUnit;

    @NotNull(message = "Dien tich khong duoc de trong")
    private LocalDate startDate;

    @NotNull(message = "Thoi gian xay dung khong duoc de trong")
    @Min(value = 1, message = "Thoi gian phai lon hon 0")
    private Integer time;

    @NotBlank(message = "Ten toa nha khong duoc de trong")
    private String timeUnit;

    @FileNotBlank(message = "Anh khong duoc de trong")
    private MultipartFile design;

    @NotBlank(message = "Ten toa nha khong duoc de trong")
    private String content;

}
