package ru.xdpxrt.vinyl.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.orderDTO.ShortOrderDTO;

import java.time.LocalDate;
import java.util.List;

import static ru.xdpxrt.vinyl.cons.Config.DATE_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullUserDTO {
    private Long id;
    private String name;
    private String email;
    @JsonFormat(pattern = DATE_FORMAT)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
    private List<ShortOrderDTO> orders;
}