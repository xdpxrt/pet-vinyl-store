package ru.xdpxrt.vinyl.dto.recordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xdpxrt.vinyl.dto.genreDTO.GenreDTO;
import ru.xdpxrt.vinyl.dto.performerDTO.ShortPerformerDTO;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullRecordDTO implements Serializable {
    private Long id;
    private String title;
    private Integer publicationYear;
    private String description;
    private String trackList;
    private ShortPerformerDTO performer;
    private Set<GenreDTO> genres;
    private Double price;
    private Integer quantity;
    private String image;
}