package com.woowacourse.document;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.woowacourse.auth.application.AuthService;
import com.woowacourse.auth.application.JwtTokenProvider;
import com.woowacourse.auth.presentation.AuthController;
import com.woowacourse.auth.presentation.AuthenticationContext;
import com.woowacourse.matzip.application.CampusService;
import com.woowacourse.matzip.application.CategoryService;
import com.woowacourse.matzip.application.RestaurantRequestService;
import com.woowacourse.matzip.application.RestaurantService;
import com.woowacourse.matzip.application.ReviewService;
import com.woowacourse.matzip.config.CampusConfig;
import com.woowacourse.matzip.domain.campus.CampusRepository;
import com.woowacourse.matzip.presentation.CampusController;
import com.woowacourse.matzip.presentation.CategoryController;
import com.woowacourse.matzip.presentation.RestaurantController;
import com.woowacourse.matzip.presentation.RestaurantRequestController;
import com.woowacourse.matzip.presentation.ReviewController;
import com.woowacourse.support.logging.ApiQueryCounter;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest({
        CategoryController.class,
        CampusController.class,
        RestaurantController.class,
        ReviewController.class,
        AuthController.class,
        RestaurantRequestController.class
})
@ExtendWith(RestDocumentationExtension.class)
public class Documentation {

    protected MockMvcRequestSpecification docsGiven;
    @MockBean
    protected ApiQueryCounter apiQueryCounter;
    @MockBean
    protected CategoryService categoryService;
    @MockBean
    protected CampusService campusService;
    @MockBean
    protected ReviewService reviewService;
    @MockBean
    protected RestaurantService restaurantService;
    @MockBean
    protected RestaurantRequestService restaurantRequestService;
    @MockBean
    protected AuthService authService;
    @MockBean
    protected JwtTokenProvider jwtTokenProvider;
    @MockBean
    protected CampusConfig campusConfig;
    @MockBean
    protected AuthenticationContext authenticationContext;
    @MockBean
    protected CampusRepository campusRepository;

    @BeforeEach
    void setDocsGiven(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        docsGiven = RestAssuredMockMvc.given()
                .mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(documentationConfiguration(restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint()))
                        .build()).log().all();
    }
}
