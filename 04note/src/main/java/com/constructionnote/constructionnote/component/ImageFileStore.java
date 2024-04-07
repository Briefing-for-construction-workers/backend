package com.constructionnote.constructionnote.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageFileStore {
    @Value("${file.dir}/")
    private String fileDirPath;

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(fileDirPath + storeFilename));

        return storeFilename;
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);

        return uuid + ext;
    }
}
