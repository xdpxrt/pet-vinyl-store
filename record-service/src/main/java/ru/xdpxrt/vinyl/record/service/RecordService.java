package ru.xdpxrt.vinyl.record.service;

import org.springframework.web.multipart.MultipartFile;
import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.dto.recordDTO.FullRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.NewRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.UpdateRecordDTO;

import java.util.List;

public interface RecordService {
    FullRecordDTO addRecord(NewRecordDTO newRecordDTO, MultipartFile cover);

    List<ShortRecordDTO> getRecords(SortType sortType, String text, Long genreId, Integer fromYear, Integer toYear,
                                    Double fromPrice, Double toPrice, Boolean onlyAvailable, Integer from, Integer size);

    FullRecordDTO updateRecord(UpdateRecordDTO updateRecordDTO, MultipartFile file, Long id);

    void deleteRecord(Long id);

    FullRecordDTO getRecord(Long id);

    List<ShortRecordDTO> getRecordsByIds(List<Long> ids);
}