package ru.xdpxrt.vinyl.kafka.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.xdpxrt.vinyl.kafka.producer.KafkaProducer;

import static ru.xdpxrt.vinyl.cons.URI.MESSAGE_URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(MESSAGE_URI)
public class MessageController {

    private final KafkaProducer kafkaProducer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public boolean sendMessage(@RequestBody String message) {
        log.info("Response from POST request on {}", MESSAGE_URI);
        kafkaProducer.send(message);
        return true;
    }
}
