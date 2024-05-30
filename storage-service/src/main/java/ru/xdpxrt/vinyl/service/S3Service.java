package ru.xdpxrt.vinyl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.xdpxrt.vinyl.error.BadRequestException;
import ru.xdpxrt.vinyl.error.ConflictException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.bucket}")
    private String bucket;
    private final S3Client s3Client;

    public String upload(MultipartFile multipartFile) {
        File file = convertFile(multipartFile);
        String filename = System.currentTimeMillis() + "_" + file.getName();
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filename)
                .build();
        s3Client.putObject(request, RequestBody.fromFile(file));
        return filename;
    }

    public byte[] download(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        try {
            return s3Client.getObject(request).readAllBytes();
        } catch (IOException exp) {
            throw new ConflictException(String.format("Key %s not found", key));
        }
    }

    public void delete(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
    }

    private File convertFile(MultipartFile file) {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        } catch (IOException exp) {
            throw new BadRequestException(String.format("Failed to convert file: %s", file.getOriginalFilename()));
        }
        return convertFile;
    }
}
