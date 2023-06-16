package com.woowacourse.document;

import com.woowacourse.matzip.application.response.CampusResponse;
import com.woowacourse.matzip.application.response.CategoryResponse;
import com.woowacourse.matzip.application.response.RestaurantDemandResponse;
import com.woowacourse.matzip.application.response.RestaurantDemandsResponse;
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

    private static final Restaurant SEOLLEUNG_RESTAURANT_1 = Restaurant.builder()
            .id(1L)
            .categoryId(1L)
            .campusId(2L)
            .name("배가무닭볶음탕")
            .address("서울 강남구 선릉로86길 30 1층")
            .distance(1L)
            .kakaoMapUrl("https://place.map.kakao.com/11190567")
            .imageUrl("www.image.com")
            .build();

    private static final Restaurant SEOLLEUNG_RESTAURANT_2 = Restaurant.builder()
            .id(2L)
            .categoryId(1L)
            .campusId(2L)
            .name("뽕나무쟁이 선릉본점")
            .address("서울 강남구 역삼로65길 31")
            .distance(1L)
            .kakaoMapUrl("https://place.map.kakao.com/11190567")
            .imageUrl("www.image.com")
            .build();

    private static final Restaurant SEOLLEUNG_RESTAURANT_3 = Restaurant.builder()
            .id(3L)
            .categoryId(2L)
            .campusId(2L)
            .name("마담밍")
            .address("서울 강남구 선릉로86길 5-4 1층")
            .distance(1L)
            .kakaoMapUrl("https://place.map.kakao.com/18283045")
            .imageUrl("www.image.com")
            .build();

    private static final Restaurant SEOLLEUNG_RESTAURANT_4 = Restaurant.builder()
            .id(4L)
            .categoryId(3L)
            .campusId(2L)
            .name("브라운돈까스 선릉점")
            .address("서울 강남구 선릉로 520")
            .distance(1L)
            .kakaoMapUrl("https://place.map.kakao.com/24449739")
            .imageUrl("www.image.com")
            .build();

    private static final Restaurant SEOLLEUNG_RESTAURANT_5 = Restaurant.builder()
            .id(5L)
            .categoryId(3L)
            .campusId(2L)
            .name("윤화돈까스")
            .address("서울 강남구 도곡로 221 셀라빌딩 1층")
            .distance(1L)
            .kakaoMapUrl("https://place.map.kakao.com/471451980")
            .imageUrl("www.image.com")
            .build();

    public static final RestaurantTitlesResponse SEOLLEUNG_RESTAURANTS_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_5, SEOLLEUNG_RESTAURANT_4, SEOLLEUNG_RESTAURANT_3, SEOLLEUNG_RESTAURANT_2,
                            SEOLLEUNG_RESTAURANT_1)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4, false))
                    .collect(Collectors.toList())
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_RESTAURANTS_SORT_BY_SPELL_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_3, SEOLLEUNG_RESTAURANT_1, SEOLLEUNG_RESTAURANT_4, SEOLLEUNG_RESTAURANT_2,
                            SEOLLEUNG_RESTAURANT_5)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4, false))
                    .collect(Collectors.toList())
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_RESTAURANTS_SORT_BY_RATING_RESPONSE = new RestaurantTitlesResponse(
            false,
            List.of(
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_1, 5, false),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_2, 4.7, false),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_3, 4.2, false),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_4, 3.7, false),
                    RestaurantTitleResponse.of(SEOLLEUNG_RESTAURANT_5, 3.0, false)
            )
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_PORT_CUTLET_RESTAURANTS_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_5, SEOLLEUNG_RESTAURANT_4)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4, false))
                    .collect(Collectors.toList())
    );

    public static final RestaurantTitlesResponse SEOLLEUNG_KOREAN_RESTAURANTS_RESPONSE = new RestaurantTitlesResponse(
            false,
            Stream.of(SEOLLEUNG_RESTAURANT_3, SEOLLEUNG_RESTAURANT_2)
                    .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4, false))
                    .collect(Collectors.toList())
    );

    public static final List<RestaurantTitleResponse> SEOLLEUNG_RESTAURANTS_RANDOM_2_RESPONSE = Stream.of(
                    SEOLLEUNG_RESTAURANT_1, SEOLLEUNG_RESTAURANT_3)
            .map(restaurant -> RestaurantTitleResponse.of(restaurant, 4, false))
            .collect(Collectors.toList());

    public static final RestaurantResponse SEOLLEUNG_RESTAURANT_RESPONSE_1 = RestaurantResponse.of(
            SEOLLEUNG_RESTAURANT_1, 4.0, false);

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
    private static final RestaurantDemandResponse RESTAURANT_REQUEST = new RestaurantDemandResponse(1L, 1L, "식당",
            "Ohzzi", false, false);

    public static final RestaurantDemandsResponse RESTAURANT_REQUESTS_RESPONSE = new RestaurantDemandsResponse(
            List.of(RESTAURANT_REQUEST), false);
}
