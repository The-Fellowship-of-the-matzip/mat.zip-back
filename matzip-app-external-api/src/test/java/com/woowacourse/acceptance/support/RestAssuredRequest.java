package com.woowacourse.acceptance.support;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class RestAssuredRequest {

    public static ExtractableResponse<Response> httpGetRequest(final String url) {
        return RestAssured
                .given().log().all()
                .when().get(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> httpGetRequest(final String url, final String accessToken) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().get(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> httpPostRequest(final String url, final String accessToken,
                                                                final Object request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(request)
                .when().post(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> httpPostRequestWithoutBody(final String url, final String accessToken) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().post(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> httpPutRequest(final String url,
                                                               final String accessToken,
                                                               final Object request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(request)
                .when().put(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> httpDeleteRequest(final String url,
                                                                  final String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().delete(url)
                .then().log().all()
                .extract();
    }


}
