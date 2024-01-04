package com.server.tistoryproject.Controller;

import com.server.tistoryproject.Model.Post;
import com.server.tistoryproject.Service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class CrawlingController {
    @Autowired
    CrawlingService crawlingService;
    public String getContent(String url) throws IOException {
        return crawlingService.getContent(url);
    }

    public String getTitle(String url) throws IOException{
        return crawlingService.getTitle(url);
    }

    public Post Selenium(String url) throws InterruptedException, IOException {
        return crawlingService.crawlingSelenium(url);
    }
}
