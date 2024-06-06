package ru.xdpxrt.vinyl.handler;

public class ConflictException extends RuntimeException {
    public ConflictException(String massage) {
        super(massage);
    }
}