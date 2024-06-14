package ru.xdpxrt.vinyl.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import ru.xdpxrt.vinyl.handler.ApiError;
import ru.xdpxrt.vinyl.handler.BadRequestException;
import ru.xdpxrt.vinyl.handler.ConflictException;
import ru.xdpxrt.vinyl.handler.NotFoundException;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FeignErrorHandler implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ApiError error;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            error = mapper.readValue(bodyIs, ApiError.class);
        } catch (IOException e) {
            return new RuntimeException("Error decoding body", e);
        }
        return switch (response.status()) {
            case 400 -> new BadRequestException(error.getMessage());
            case 404 -> new NotFoundException(error.getMessage());
            case 406 -> new ConflictException(error.getMessage());
            default -> new RuntimeException(error.getMessage());
        };
    }
}
