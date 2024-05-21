package ru.xdpxrt.vinyl.error;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String massage) {
        super(massage);
    }
}