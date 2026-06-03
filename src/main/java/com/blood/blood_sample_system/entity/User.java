package com.blood.blood_sample_system.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @Column(nullable = false)
   private String name;
   @Column(nullable = false,unique = true)
    private String email;
   @Column(nullable = false)
   private String password;
   private String phone;
   private String address;
   @Enumerated(EnumType.STRING)
  private Role role=Role.USER;
    public enum Role{
       USER,ADMIN
    }

}
