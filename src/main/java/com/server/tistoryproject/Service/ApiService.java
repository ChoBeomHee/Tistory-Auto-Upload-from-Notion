package com.server.tistoryproject.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.tistoryproject.Model.Post;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {
    String access_token = "122a39501e16815a3067134136cf126f_f32160a6fadafb76e165c7fad39c64ad";
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

    public void origin_write(Post post){
        String url = "https://www.tistory.com/apis/post/write?";
//        url += "access_token=" + access_token + "&";
//        url += "output=" + "json" + "&";
//        url += "blogName=" + "tigerfrom2" + "&";
//        url += "title=" + post.getTitle() + "&";
//        url += "content=" + post.getContent() + "&";
//        url += "category=1372431";
        // 요청할 데이터 (JSON 예제)

        Map<String, Object> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("output", "json");
        params.put("blogName", "tigerfrom2");
        params.put("title", post.getTitle());
        params.put("content", post.getContent());
        params.put("category", "1372431");
        System.out.println(post.getContent());
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

        System.out.println("Response Code: " + responseEntity.getStatusCode());
        System.out.println("Response Body: " + responseEntity.getBody());
    }
    public boolean getCategory(String Category) throws IOException {
        RestClient restClient = RestClient.create();
        String url = "https://www.tistory.com/apis/category/list?";
        url += "access_token=" + access_token + "&";
        url += "output=" + "json" + "&";
        url += "blogName=" + "tigerfrom2";

        String response = (restClient.get().uri(url).accept(MediaType.APPLICATION_JSON).
                retrieve().toEntity(String.class).getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map> res = objectMapper.readValue(response, new TypeReference<>() {
        });

        Map<String, Map> res_item = (Map<String, Map>) res.get("tistory").get("item");

        ArrayList<Map> arrayList = (ArrayList<Map>) res_item.get("categories");
        System.out.println(res_item);
        try {
            if(!search_category(arrayList, Category)) throw new Exception();
        }catch (Exception e){
            return false;
        }

        return true;
    }

    boolean search_category(ArrayList<Map> arrayList, String Aim_Category_name){
        for (Map map : arrayList) {
            if (map.get("label").equals(Aim_Category_name)) return true;
        }

        return false;
    }
}
