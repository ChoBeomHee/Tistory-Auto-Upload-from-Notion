package com.server.tistoryproject.DTO;

import lombok.Data;

@Data
public class ModifyDTO {
    String postId;
    String title;
    String url;

    public ModifyDTO(String Id, String title, String url){
        this.postId = Id;
        this.title = title;
        this.url = url;
    }

    public ModifyDTO() {

    }
}
