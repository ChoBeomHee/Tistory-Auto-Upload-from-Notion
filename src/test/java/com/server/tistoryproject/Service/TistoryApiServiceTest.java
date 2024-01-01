package com.server.tistoryproject.Service;

import com.server.tistoryproject.DTO.CategoryDTO;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TistoryApiServiceTest {

    @Test
    public void testSearchCategory() {
        // 테스트에 필요한 데이터를 생성합니다.
        ArrayList<Map> arrayList = new ArrayList<>();
        Map<String, String> category1 = new HashMap<>();
        category1.put("label", "Category1");
        category1.put("id", "1");
        arrayList.add(category1);

        Map<String, String> category2 = new HashMap<>();
        category2.put("label", "Category2");
        category2.put("id", "2");
        arrayList.add(category2);

        // 테스트할 서비스 객체를 생성합니다.
        TistoryApiService tistoryApiService = new TistoryApiService();

        // 테스트 메서드 호출
        CategoryDTO result = tistoryApiService.search_category(arrayList, "Category1");

        // 검증
        assertNotNull(result);
        assertEquals("Category1", result.getCategory_Name());
        assertEquals("1", result.getId());
    }
}
