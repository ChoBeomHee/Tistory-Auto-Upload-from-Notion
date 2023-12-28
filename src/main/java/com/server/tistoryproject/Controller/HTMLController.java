package com.server.tistoryproject.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class HTMLController {
    @GetMapping("/image/") // image/?FolderName=aaa&FileName=bbb
    public ResponseEntity<byte[]> getImage(@RequestParam String FolderName, @RequestParam String FileName) throws IOException {
        String path = "C:\\Users\\tiger\\OneDrive\\바탕 화면\\TistoryProjectServer\\TistoryProject\\src\\main\\resources\\static\\webapp\\Img_file\\";
        path = path + FolderName + "\\" + FileName;
        // 이미지 파일을 클래스 패스에서 읽어옵니다.
        URLEncoder.encode(path, StandardCharsets.UTF_8);
        byte[] imageBytes = Files.readAllBytes(Path.of(path));
        // HTTP 응답에 이미지를 포함하여 반환합니다.
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }
}
