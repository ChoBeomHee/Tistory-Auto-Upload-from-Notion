package com.server.tistoryproject.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class ImageController {
    @GetMapping("/example")
    public ResponseEntity<byte[]> getImage() throws IOException {
        String path = "C:\\Users\\tiger\\OneDrive\\바탕 화면\\TistoryProjectServer\\TistoryProject\\src\\main\\resources\\static\\webapp\\Img_file\\SEAL Library 코드 분석 및 정리 da4d636a2a3a48e485629a018b369a2e\\Untitled.png";

        // 이미지 파일을 클래스 패스에서 읽어옵니다.
        URLEncoder.encode(path, StandardCharsets.UTF_8);
        Resource resource = new ClassPathResource(path);

        byte[] imageBytes = Files.readAllBytes(Path.of(path));

        // HTTP 응답에 이미지를 포함하여 반환합니다.
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }
}
