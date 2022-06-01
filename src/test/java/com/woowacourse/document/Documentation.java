package com.woowacourse.document;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.woowacourse.auth.application.AuthService;
import com.woowacourse.auth.presentation.AuthController;
import com.woowacourse.matzip.application.CategoryService;
import com.woowacourse.matzip.presentation.CategoryController;
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
        AuthController.class
})
@ExtendWith(RestDocumentationExtension.class)
class Documentation {

    protected MockMvcRequestSpecification docsGiven;

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

    @MockBean
    protected CategoryService categoryService;

    @MockBean
    protected AuthService authService;
}
