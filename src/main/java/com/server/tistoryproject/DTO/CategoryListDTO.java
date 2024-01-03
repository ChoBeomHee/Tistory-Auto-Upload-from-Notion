package com.server.tistoryproject.DTO;

import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

@Data
public class CategoryListDTO {
    ArrayList<Category> categories = new ArrayList<>();

    public CategoryListDTO(JSONArray jsonArray){
        for(Object jsonObject : jsonArray){
            categories.add(new Category((JSONObject) jsonObject));
        }
    }

    @Data
    public static class Category{
        String id;
        String label;
        String parent;
        String entries;
        String entriesInLogin;

        Category(JSONObject jsonObject){
            this.id = (String) jsonObject.get("id");
            this.label = (String) jsonObject.get("label");
            this.parent = (String) jsonObject.get("parent");
            this.entries = (String) jsonObject.get("entries");
            this.entriesInLogin = (String) jsonObject.get("entriesInLogin");
        }
    }
}
