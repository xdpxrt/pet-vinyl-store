package ru.xdpxrt.vinyl.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.service.EmailSenderService;

import static ru.xdpxrt.vinyl.cons.URI.MAIL_URI;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(MAIL_URI)
public class Controller {

    private final EmailSenderService service;

    @PostMapping
    public void send(@RequestBody @NotBlank String text,
                     @RequestParam @Email String email) {
        log.info("Sending email: {}", email);
        service.sendEmail(email, text);
    }
}