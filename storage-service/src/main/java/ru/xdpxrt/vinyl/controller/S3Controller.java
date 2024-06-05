package ru.xdpxrt.vinyl.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.xdpxrt.vinyl.service.S3Service;

import static ru.xdpxrt.vinyl.cons.URI.KEY_URI;
import static ru.xdpxrt.vinyl.cons.URI.STORAGE_URI;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(STORAGE_URI)
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String upload(@RequestPart MultipartFile file) {
        log.info("Uploading file {}", file.getOriginalFilename());
        return s3Service.upload(file);
    }

    @GetMapping(KEY_URI)
    public byte[] download(@PathVariable @NotBlank String key) {
        log.info("Downloading file {}", key);
        return s3Service.download(key);
    }

    @DeleteMapping(KEY_URI)
    public void delete(@PathVariable @NotBlank String key) {
        log.info("Deleting file {}", key);
        s3Service.delete(key);
    }
}