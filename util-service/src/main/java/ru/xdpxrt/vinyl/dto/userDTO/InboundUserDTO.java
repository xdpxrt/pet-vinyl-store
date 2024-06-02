package ru.xdpxrt.vinyl.dto.userDTO;

import jakarta.validation.constraints.Email;
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
public class InboundUserDTO {
    private String name;
    @Email
    private String email;
    private String password;
    private LocalDate birthday;
    private Role role;
}