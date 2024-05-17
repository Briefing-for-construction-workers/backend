package com.constructionnote.constructionnote.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.constructionnote.constructionnote.dto.user.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class S3FileStore {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public FileDto storeFile(MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) {
            return null;
        }
        return uploadToS3(multipartFile);
    }

    private FileDto uploadToS3(MultipartFile multipartFile) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename();
        String storedFileName = createStoreFilename(originalFilename);

        //metadata 생성
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        //S3로 요청
        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, storedFileName, inputStream, objectMetadata));
        }catch (Exception e){
            throw new Exception("Can't upload file");
        }

        String uploadFileUrl = amazonS3Client.getUrl(bucket, storedFileName).toString();

        FileDto fileDto = FileDto.builder()
                .imageUrl(uploadFileUrl)
                .fileName(storedFileName)
                .build();

        return fileDto;
    }

    public void deleteFile(String filePath) throws Exception {
        try {
            amazonS3Client.deleteObject(bucket, filePath);
        } catch (Exception e) {
            throw new Exception("Can't delete file");
        }
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);

        return uuid + ext;
    }
}
