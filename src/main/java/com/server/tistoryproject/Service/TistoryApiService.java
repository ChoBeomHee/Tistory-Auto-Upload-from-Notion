package com.server.tistoryproject.Service;

import com.server.tistoryproject.DTO.CategoryDTO;
import com.server.tistoryproject.DTO.CategoryListDTO;
import com.server.tistoryproject.DTO.ModifyDTO;
import com.server.tistoryproject.Model.Post;
import com.server.tistoryproject.Model.TistoryTokenInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.server.tistoryproject.Model.TistoryTokenInfo.*;

@Service
public class TistoryApiService {
    @Autowired
    CrawlingService crawlingService;
    public String getToken(String Code){
        String url = "https://www.tistory.com/oauth/access_token?"
                + "client_id=" + "4b0b689fe13b9041ca50a4fe7cef06fc" + "&"
                + "client_secret=" + "4b0b689fe13b9041ca50a4fe7cef06fc72139881411dac60de9ee254d05658e857cef67e" +"&"
                + "redirect_uri=" + "https://tigerfrom2.tistory.com/" + "&"
                + "code=" + Code + "&"
                + "grant_type=" + "authorization_code";

        RestClient restClient = RestClient.create();
        String Token = restClient.get().uri(url).retrieve().body(String.class);
        System.out.println("==================토큰" + Token);
        return Token;
    }
    public void write(){
        String Url = "";
        RestClient restClient = RestClient.create();
        ResponseEntity response = restClient.post()
                .uri(Url)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();

        System.out.println(response);
    }

    public void origin_write(Post post, String categoryId) throws ParseException, IOException {
        String url = "https://www.tistory.com/apis/post/write?";

        Map<String, Object> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("output", output);
        params.put("blogName", blogName);
        params.put("title", post.getTitle());
        params.put("content", post.getContent());
        params.put("category", String.valueOf(categoryId));
        params.put("visibility", visibility);
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

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody());
        JSONObject value = (JSONObject) jsonObject.get("tistory");
        ModifyDTO modifyDTO = new ModifyDTO((String) value.get("postId"), post.getTitle(), (String) value.get("url"));

        post_modify(modifyDTO);
    }
    public CategoryDTO getCategory(String Category) throws IOException, ParseException {
        RestClient restClient = RestClient.create();
        String url = "https://www.tistory.com/apis/category/list?";
        url += "access_token=" + access_token + "&";
        url += "output=" + "json" + "&";
        url += "blogName=" + "tigerfrom2";

        String response = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);


        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject Tistory_Information = (JSONObject) jsonObject.get("tistory");
        JSONObject Items = (JSONObject) Tistory_Information.get("item");
        JSONArray CategoryArray = (JSONArray) Items.get("categories");

        CategoryListDTO categoryListDTO = new CategoryListDTO(CategoryArray);
        CategoryDTO categoryDTO = search_category(categoryListDTO.getCategories(), Category);
        try {
            if (categoryDTO == null) {
                throw new NoSuchElementException("카테고리가 존재하지 않음");
            }
        } catch (NoSuchElementException e) {
            return new CategoryDTO("0", "0");
        }

        return categoryDTO;
    }

    CategoryDTO search_category(ArrayList<CategoryListDTO.Category> arrayList, String Category_name){
        for (CategoryListDTO.Category category : arrayList) {
            if (category.getLabel().contains(Category_name)){
                CategoryDTO categoryDTO = new CategoryDTO();

                categoryDTO.setCategory_Name(category.getLabel());
                categoryDTO.setId(category.getId());

                return categoryDTO;
            }
        }

        return null;
    }

    void post_modify(ModifyDTO modifyDTO) throws IOException, ParseException {
        String url = "https://www.tistory.com/apis/post/modify?";
        String id = modifyDTO.getPostId();
        String title = modifyDTO.getTitle();
        String content = crawlingService.getContent(modifyDTO.getUrl());

        Map<String, Object> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("output", output);
        params.put("blogName", blogName);
        params.put("content", content + "*이 글은 노션에서 자동으로 업로드 되었습니다.*");
        params.put("title", title);
        params.put("postId", id);
        params.put("visibility", visibility);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        System.out.println(responseEntity.getBody());

        System.out.println("글 튜닝");
    }
}
