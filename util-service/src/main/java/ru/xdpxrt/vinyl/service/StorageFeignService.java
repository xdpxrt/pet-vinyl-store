package ru.xdpxrt.vinyl.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static ru.xdpxrt.vinyl.cons.URI.KEY_URI;
import static ru.xdpxrt.vinyl.cons.URI.STORAGE_URI;

@Validated
@FeignClient("STORAGE-SERVICE")
public interface StorageFeignService {

    @PostMapping(path = STORAGE_URI, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String upload(@RequestPart MultipartFile file);

    @GetMapping(STORAGE_URI + KEY_URI)
    public byte[] download(@PathVariable @NotBlank String key);

    @DeleteMapping(STORAGE_URI + KEY_URI)
    public void delete(@PathVariable @NotBlank String key);
}