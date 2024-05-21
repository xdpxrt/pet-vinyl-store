package ru.xdpxrt.vinyl.record.service;

import ru.xdpxrt.vinyl.cons.SortType;
import ru.xdpxrt.vinyl.dto.recordDTO.FullRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.NewRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.ShortRecordDTO;
import ru.xdpxrt.vinyl.dto.recordDTO.UpdateRecordDTO;

import java.util.List;

public interface RecordService {
    FullRecordDTO addRecord(NewRecordDTO newRecordDTO);

    List<ShortRecordDTO> getRecords(SortType sortType, String text, Long genreId, Integer fromYear, Integer toYear,
                                    Double fromPrice, Double toPrice, Boolean available, Integer from, Integer Size);

    FullRecordDTO updateRecord(UpdateRecordDTO updateRecordDTO, Long id);

    void deleteRecord(Long id);

    FullRecordDTO getRecord(Long id);
}