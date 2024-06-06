package ru.xdpxrt.vinyl.handler;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String massage) {
        super(massage);
    }
}