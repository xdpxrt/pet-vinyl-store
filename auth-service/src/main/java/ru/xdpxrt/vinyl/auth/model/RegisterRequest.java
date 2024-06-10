package ru.xdpxrt.vinyl.auth.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.cons.Role;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 5, max = 20)
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private Role role;
}