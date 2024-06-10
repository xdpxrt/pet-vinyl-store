package ru.xdpxrt.vinyl.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullUserDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    private List<ShortOrderDTO> orders;
}