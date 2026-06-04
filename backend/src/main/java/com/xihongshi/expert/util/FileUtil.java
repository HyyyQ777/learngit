package com.xihongshi.expert.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file, String prefix) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("文件为空");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("文件大小不能超过5MB");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String[] allowedTypes = {"jpg", "jpeg", "png", "gif"};
        String fileExt = extension.toLowerCase().replace(".", "");
        boolean allowed = false;
        for (String type : allowedTypes) {
            if (type.equals(fileExt)) {
                allowed = true;
                break;
            }
        }
        if (!allowed) {
            throw new RuntimeException("不支持的文件类型，仅支持jpg、jpeg、png、gif");
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = prefix + "_" + UUID.randomUUID().toString().substring(0, 8) + extension;
        File destFile = new File(dir, filename);
        file.transferTo(destFile);
        return "/uploads/" + filename;
    }
}
