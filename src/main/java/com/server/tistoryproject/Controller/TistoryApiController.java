package com.server.tistoryproject.Controller;

import com.server.tistoryproject.DTO.CategoryDTO;
import com.server.tistoryproject.DTO.ModifyDTO;
import com.server.tistoryproject.Model.Post;
import com.server.tistoryproject.Service.TistoryApiService;
import com.server.tistoryproject.Service.HTMLService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.server.tistoryproject.Model.TistoryTokenInfo.*;

@RestController
public class TistoryApiController {
    @Autowired
    private TistoryApiService tistoryApiService;

    @Autowired  
    private HTMLService HTMLService;

    @Autowired
    private CrawlingController crawlingController;

    @PostMapping("v1/upload") // 동적 페이지 전용
    String AutoTistoryUpload(@RequestParam HashMap<String, String> URL) throws IOException, InterruptedException, ParseException {
        String url = URL.get("url_info");
        String category = URL.get("category");


        Post post = new Post();
        try {
            post = crawlingController.Selenium(url);
        } catch (Exception e) {
            post = crawlingController.Selenium(url);
        }
        Thread.sleep(5000);

        post.setContent(HTMLService.HTMLFileLoad(post.getDownload_filename()));

        CategoryDTO categoryDTO = tistoryApiService.getCategory(category);

        tistoryApiService.origin_write(post, categoryDTO.getId());

        return "노션 글 티스토리 쓰기";
    }
//    @PostMapping ("/write") // 정적 페이지 전용
//    String write(@RequestParam HashMap<String, String> userInfo) throws IOException {
//        Post post = new Post();
//        String url = userInfo.get("userInput");
//
//        post.setTitle(crawlingController.getTitle(url));
//        post.setContent(crawlingController.getContent(url));
//
//        tistoryApiService.origin_write(post);
//
//        return "글쓰기에 성공하였습니다.";
//    }
//
//    @GetMapping("/getCategory/{Category}")
//    String getCategory(@PathVariable String Category) throws IOException {
//        System.out.println(Category);
//        if(tistoryApiService.getCategory(Category)) return Category;
//        else return "카테고리 없음";
//    }
}
