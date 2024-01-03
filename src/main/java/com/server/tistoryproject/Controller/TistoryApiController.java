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

        Post post = crawlingController.Selenium(url); // HTML 코드 여기서 가져옴
        Thread.sleep(5000);

        post.setContent(HTMLService.HTMLFileLoad(post.getDownload_filename()));

        CategoryDTO categoryDTO = tistoryApiService.getCategory(category);

        tistoryApiService.origin_write(post, categoryDTO.getId());

        return "노션 글 티스토리 쓰기";
    }


    @GetMapping("TEST")
    String TEST() throws IOException, ParseException {
        String url = "https://www.tistory.com/apis/post/modify?";

        String Content = crawlingController.crawlingService.getContent("https://tigerfrom2.tistory.com/362");
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("output", output);
        params.put("blogName", blogName);
        params.put("content", Content + "\n\n*이 글은 노션에서 자동으로 업로드 되었습니다.*");
        params.put("title", "수정222");
        params.put("postId", "362");
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔터티 생성
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();
        // POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        System.out.println(responseEntity.getBody());
        return "TEST";
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
