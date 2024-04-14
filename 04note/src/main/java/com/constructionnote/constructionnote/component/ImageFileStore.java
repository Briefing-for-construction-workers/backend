package com.constructionnote.constructionnote.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    public byte[] getFile(String filePath) throws Exception {
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            inputStream = new FileInputStream(fileDirPath + filePath);
        }
        catch (FileNotFoundException e) {
            throw new Exception("Can't find file");
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try {
            while((readCount = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, readCount);
            }
            fileArray = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();

        }
        catch (IOException e) {
            throw new Exception("Can't convert file");
        }

        return fileArray;
    }
}
