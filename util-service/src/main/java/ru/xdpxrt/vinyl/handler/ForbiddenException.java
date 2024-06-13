package ru.xdpxrt.vinyl.handler;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String massage) {
        super(massage);
    }
}