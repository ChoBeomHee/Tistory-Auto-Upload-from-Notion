package com.server.tistoryproject.Controller;

import com.server.tistoryproject.DTO.PostUrlDTO;
import com.server.tistoryproject.DTO.WriteDTO;
import com.server.tistoryproject.Model.Post;
import com.server.tistoryproject.Service.ApiService;
import com.server.tistoryproject.Service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class ApiController {
    @Autowired
    private ApiService apiService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CrawlingController crawlingController;

    @PostMapping("v1/upload") // 동적 페이지 전용
    String test_sel(@RequestParam HashMap<String, String> URL) throws IOException, InterruptedException {
        System.out.println(URL);
        String url = URL.get("url_info");

        Post post = crawlingController.Selenium(url);
        Thread.sleep(5000);

        post.setContent(uploadService.HTMLFileLoad());

        apiService.origin_write(post);

        return "노션 글 티스토리 쓰기";
    }

    @PostMapping ("/write") // 정적 페이지 전용
    String write(@RequestParam HashMap<String, String> userInfo) throws IOException {
        Post post = new Post();
        String url = userInfo.get("userInput");

        post.setTitle(crawlingController.getTitle(url));
        post.setContent(crawlingController.getContent(url));

        apiService.origin_write(post);

        return "글쓰기에 성공하였습니다.";
    }

    @GetMapping("/getCategory/{Category}")
    String getCategory(@PathVariable String Category) throws IOException {
        System.out.println(Category);
        if(apiService.getCategory(Category)) return Category;
        else return "카테고리 없음";
    }
}
