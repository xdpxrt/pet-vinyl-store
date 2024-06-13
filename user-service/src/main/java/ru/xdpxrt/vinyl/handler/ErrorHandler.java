package ru.xdpxrt.vinyl.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;
import static ru.xdpxrt.vinyl.cons.Config.FORMATTER;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(UNAUTHORIZED)
    public ApiError handleUnauthorizedException(UnauthorizedException e) {
        return new ApiError(UNAUTHORIZED.name(), e.getMessage(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public ApiError handleForbiddenException(ForbiddenException e) {
        return new ApiError(FORBIDDEN.name(), e.getMessage(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleValidationException(BadRequestException e) {
        return new ApiError(BAD_REQUEST.name(), e.getMessage(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException e) {
        return new ApiError(NOT_FOUND.name(), e.getMessage(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler({DataAccessException.class, ConflictException.class})
    @ResponseStatus(CONFLICT)
    public ApiError handleConflictException(RuntimeException e) {
        return new ApiError(CONFLICT.name(), e.getMessage(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiError handleServerException(RuntimeException e) {
        return new ApiError(INTERNAL_SERVER_ERROR.name(), e.getMessage(), LocalDateTime.now().format(FORMATTER));
    }
}