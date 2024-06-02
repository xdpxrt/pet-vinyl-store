//package ru.xdpxrt.vinyl.auth.model;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import ru.xdpxrt.vinyl.user.model.Role;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class RegisterRequest {
//    @NotBlank
//    @Max(20)
//    private String name;
//    @Email
//    private String email;
//    @NotNull
//    private String password;
//    @NotNull
//    private Role role;
//}