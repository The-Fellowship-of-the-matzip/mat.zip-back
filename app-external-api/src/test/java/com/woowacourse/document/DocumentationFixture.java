package com.woowacourse.document;

import com.woowacourse.matzip.application.response.CampusResponse;
import com.woowacourse.matzip.application.response.CategoryResponse;
import com.woowacourse.matzip.application.response.RestaurantRequestResponse;
import com.woowacourse.matzip.application.response.RestaurantRequestsResponse;
import com.woowacourse.matzip.application.response.RestaurantResponse;
import com.woowacourse.matzip.application.response.RestaurantTitleResponse;
import com.woowacourse.matzip.application.response.RestaurantTitlesResponse;
import com.woowacourse.matzip.application.response.ReviewResponse;
import com.woowacourse.matzip.application.response.ReviewResponse.ReviewAuthor;
import com.woowacourse.matzip.application.response.ReviewsResponse;
import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
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

    private static final Restaurant SEOLLEUNG_RESTAURANT_1 = new Restaurant(1L, 1L, 2L, "배가무닭볶음탕",
            "서울 강남구 선릉로86길 30 1층", 1L,
            "https://place.map.kakao.com/733513512", "www.image.com");
    private static final Restaurant SEOLLEUNG_RESTAURANT_2 = new Restaurant(2L, 1L, 2L, "뽕나무쟁이 선릉본점",
            "서울 강남구 역삼로65길 31", 1L,
            "https://place.map.kakao.com/11190567", "www.image.com");
    private static final Restaurant SEOLLEUNG_RESTAURANT_3 = new Restaurant(3L, 2L, 2L, "마담밍",
            "서울 강남구 선릉로86길 5-4 1층", 1L,
            "https://place.map.kakao.com/18283045", "www.image.com");
    private static final Restaurant SEOLLEUNG_RESTAURANT_4 = new Restaurant(4L, 3L, 2L, "브라운돈까스 선릉점",
            "서울 강남구 선릉로 520", 1L,
            "https://place.map.kakao.com/24449739", "www.image.com");
    private static final Restaurant SEOLLEUNG_RESTAURANT_5 = new Restaurant(5L, 3L, 2L, "윤화돈까스",
            "서울 강남구 도곡로 221 셀라빌딩 1층", 1L,
            "https://place.map.kakao.com/471451980", "www.image.com");

    public static final RestaurantTitlesResponse SEOLLEUNG_RESTAURANTS_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_5, SEOLLEUNG_RESTAURANT_4, SEOLLEUNG_RESTAURANT_3, SEOLLEUNG_RESTAURANT_2,
                            SEOLLEUNG_RESTAURANT_1)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4))
                    .collect(Collectors.toList())
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_RESTAURANTS_SORT_BY_SPELL_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_3, SEOLLEUNG_RESTAURANT_1, SEOLLEUNG_RESTAURANT_4, SEOLLEUNG_RESTAURANT_2,
                            SEOLLEUNG_RESTAURANT_5)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4))
                    .collect(Collectors.toList())
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_RESTAURANTS_SORT_BY_RATING_RESPONSE = new RestaurantTitlesResponse(
            false,
            List.of(
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_1, 5),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_2, 4.7),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_3, 4.2),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_4, 3.7),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_5, 3.0)
            )
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_PORT_CUTLET_RESTAURANTS_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_5, SEOLLEUNG_RESTAURANT_4)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4))
                    .collect(Collectors.toList())
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_KOREAN_RESTAURANTS_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_3, SEOLLEUNG_RESTAURANT_2)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4))
                    .collect(Collectors.toList())
    );

    public static final List<RestaurantTitleResponse> SEOLLEUNG_RESTAURANTS_RANDOM_2_RESPONSE = Stream.of(
                    SEOLLEUNG_RESTAURANT_1, SEOLLEUNG_RESTAURANT_3)
            .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4))
            .collect(Collectors.toList());

    public static final RestaurantResponse SEOLLEUNG_RESTAURANT_RESPONSE_1 = RestaurantResponse.of(
            SEOLLEUNG_RESTAURANT_1, 4.0);

    private static final ReviewResponse REVIEW_1 = new ReviewResponse(1L, new ReviewAuthor("후니", "url"), "무가 맛있어요", 5,
            "무닭볶음탕 (중)", true);
    private static final ReviewResponse REVIEW_2 = new ReviewResponse(2L, new ReviewAuthor("오찌", "url"), "맛없어요.", 2,
            "무닭볶음탕 (대)", false);
    private static final ReviewResponse REVIEW_3 = new ReviewResponse(3L, new ReviewAuthor("태태", "url"), "평범해요.", 3,
            "무닭볶음탕 (중)", false);
    private static final ReviewResponse REVIEW_4 = new ReviewResponse(4L, new ReviewAuthor("샐리", "url"), "맛있어요.", 4,
            "무닭볶음탕 (대)", false);
    private static final ReviewResponse REVIEW_5 = new ReviewResponse(5L, new ReviewAuthor("블링", "url"), "또오고 싶어요.", 5,
            "통마늘 닭똥집볶음", false);

    public static final ReviewsResponse REVIEWS_RESPONSE = new ReviewsResponse(
            false, List.of(REVIEW_1, REVIEW_2, REVIEW_3, REVIEW_4, REVIEW_5)
    );
    private static final RestaurantRequestResponse RESTAURANT_REQUEST = new RestaurantRequestResponse(1L, 1L, "식당",
            "Ohzzi", false, false);

    public static final RestaurantRequestsResponse RESTAURANT_REQUESTS_RESPONSE = new RestaurantRequestsResponse(
            List.of(RESTAURANT_REQUEST), false);
}
