package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHubProject {

    String baseURI = "https://api.github.com/user/keys";
    int id;
    RequestSpecification requestSpec;

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","token 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")
                .build();
    }

    @Test(priority = 1)
    public void postRequest(){

        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc3ACCAABBTQBCCABAQDteyn6vL3PT4u/mu97ZIiW3LUimF+WlJPcZHnDFQ+9sj6A1+fOJtlhGmrLZcZRx+DQrZejdSLlqa/pDmoNX5clUYK+Y7CU46lBtwbnLtc9VtE4dTTQSF+4jo7d3PiaNaI5XYR8ySVNvLAC5HjmC+l20hwRDENnWrLaCVMyX9NFSXDs1OVg/nrBa+7mrqXAZYfqXu/5zN9TntKyofItefa1GymnrXrTsp88CF1HgSz1inOHg0y7JT8ChH+ibTx0v0tAv/sdfB5xq++8yon6qlrekunj8XAK3h2QTsuw8kD4xniWaN7XeEkRhfSCw3CS41/y3l+SyhXHSzqYpdlUS19W\"}";

        Response res = given().spec(requestSpec)
                .body(reqBody).when().post();
        System.out.println(res.getBody().asString());
        id = res.then().extract().path("id");
        res.then().statusCode(201);

    }

    @Test(priority = 2)
    public void getRequest(){
        Response res = given().spec(requestSpec)
                .pathParam("keyId",id)
                .when().get("/{keyId}");
        res.then().log().all().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteRequest(){
        Response res = given().spec(requestSpec)
                    .pathParam("keyId",id)
                    .when().delete("/{keyId}");
        res.then().log().all().statusCode(204);
    }
}