package ru.xdpxrt.vinyl.error;

public class ConflictException extends RuntimeException {
    public ConflictException(String massage) {
        super(massage);
    }
}