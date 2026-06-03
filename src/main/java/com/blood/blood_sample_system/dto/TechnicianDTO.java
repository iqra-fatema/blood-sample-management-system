package com.blood.blood_sample_system.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class TechnicianDTO {
    @Data
    public static class TechnicianRequest{
       @NotBlank(message = "Name is required")
       private String name;
       @NotBlank(message = "Phone is required")
       @Size(min=10, max=10,message = "Phone must be 10 digits")
       private String phone;
      @Email(message = "Enter valid email")
      @NotBlank(message = "Email is required")
       private String email;
        @NotBlank(message = "City is required")
        private String city;
         private Integer maxVisitsPerDay=6;

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TechnicianResponse{
       private long id;
       private String name;
        private String phone;
        private String email;
        private String city;
        private String status;
         private Integer maxVisitsPerDay;


    }
}
