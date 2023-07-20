package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/api/v1")
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    private ApiHelper() {
    }

    public static String paymentStatus(DataHelper.CardInfo cardInfo, int statusCode) {
        Response response =
                given()
                        .spec(requestSpec)
                        .body(cardInfo)
                        .post("/pay")
                        .then()
                        .contentType(ContentType.JSON)
                        .statusCode(statusCode)
                        .extract().response();
        return response.jsonPath().getString("status");

    }


}
