package com.server.tistoryproject.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    String Id;
    String Category_Name;

    public CategoryDTO(String Id, String CategoryName) {
        this.Id = Id;
        this.Category_Name = CategoryName;
    }
}
