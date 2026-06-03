package com.blood.blood_sample_system.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class TestPackageDTO {
    @Data
public static class TestPackageRequest{
    @NotBlank(message = "Testname is required")
    private String name;
    private String description;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    @NotBlank(message = "Sample type is required")
    private String sampleType;
    private Integer reportTimeHours;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
public static class TestPackageResponse{
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String sampleType;
    private Integer reportTimeHours;


}
}
