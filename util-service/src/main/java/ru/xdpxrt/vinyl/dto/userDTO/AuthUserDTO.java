package ru.xdpxrt.vinyl.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.cons.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}