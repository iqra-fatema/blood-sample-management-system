package com.blood.blood_sample_system.dto;
import jakarta.validation.constraints.*;
import lombok.*;
public class AuthDTO {
    //REGISTRATION FORM
  @Data
  public static class RegisterRequest{
       @NotBlank(message = "Name is required")
       private String name;
       @Email(message ="Enter valid Email")
       @NotBlank(message = "Email is required")
       private String email;
       @NotBlank(message = "Password is required")
       @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
               message = "Password must have minimum 8 characters, one uppercase, one lowercase, one number and one special character")
       private String password;
       @NotBlank(message = "Address is required.")
       private String address;
        @NotBlank(message = "Phone is required")
        @Size(min = 10,max = 10,message = "Phone must be 10 diits")
       private String phone;
   }
   //LOGIN FORM
    @Data
    public static class LoginRequest{
        @Email(message ="Enter valid Email")
        @NotBlank(message = "Email is required")
        private String email;
        @NotBlank(message = "Password is required.")
        private String password;
    }
    // RESPONSE
    @Data
    @AllArgsConstructor
    public static class AuthResponse{
      private String token;
      private String name;
      private String email;
      private String role;
    }
}
