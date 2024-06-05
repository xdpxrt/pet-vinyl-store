package ru.xdpxrt.vinyl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.xdpxrt.vinyl.dto.messageDTO.MessageDTO;

import static ru.xdpxrt.vinyl.cons.Config.BIRTHDAY_TOPIC;
import static ru.xdpxrt.vinyl.cons.Config.ORDERS_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    @Value("${spring.mail.username}")
    private String mailFrom;
    @Value("${spring.mail.subject}")
    private String subject;
    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = {ORDERS_TOPIC, BIRTHDAY_TOPIC})
    public void sendEmail(MessageDTO messageDTO) {
        log.debug("Sending email to {}", messageDTO.getEmail());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(messageDTO.getEmail());
        message.setSubject(subject);
        message.setText(messageDTO.getMessage());
        javaMailSender.send(message);
    }
}