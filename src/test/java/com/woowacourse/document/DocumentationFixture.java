package com.woowacourse.document;

import com.woowacourse.matzip.application.response.CampusResponse;
import com.woowacourse.matzip.application.response.CategoryResponse;
import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentationFixture {

    private static final Category CATEGORY_1 = new Category(1L, "한식");
    private static final Category CATEGORY_2 = new Category(2L, "중식");
    private static final Category CATEGORY_3 = new Category(3L, "일식");
    private static final Category CATEGORY_4 = new Category(4L, "양식");
    private static final Category CATEGORY_5 = new Category(5L, "카페/디저트");

    public static final List<CategoryResponse> CATEGORY_RESPONSES = Stream.of(CATEGORY_1, CATEGORY_2, CATEGORY_3,
                    CATEGORY_4, CATEGORY_5)
            .map(CategoryResponse::from)
            .collect(Collectors.toList());

    private static final Campus CAMPUS_1 = new Campus(1L, "잠실");
    private static final Campus CAMPUS_2 = new Campus(2L, "선릉");

    public static final List<CampusResponse> CAMPUS_RESPONSES = Stream.of(CAMPUS_1, CAMPUS_2)
            .map(CampusResponse::from)
            .collect(Collectors.toList());
}
