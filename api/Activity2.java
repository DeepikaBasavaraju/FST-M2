package activities;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

    public class Activity2 {
        final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

        @Test(priority=1)
        public void addNewUserFromFile() throws IOException {
            FileInputStream inputJSON = new FileInputStream("src/test/java/activities/userinfo.json");
            String reqBody = new String(inputJSON.readAllBytes());

            Response response =
                    given().contentType(ContentType.JSON) // Set headers
                            .body(reqBody) // Pass request body from file
                            .when().post(ROOT_URI); // Send POST request
            inputJSON.close();
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("9021"));
        }

        @Test(priority=2)
        public void getUserInfo() {
            File outputJSON = new File("src/test/java/activities/userGETResponse.json");

            Response response =
                    given().contentType(ContentType.JSON) // Set headers
                            .pathParam("username", "deepika03") // Pass request body from file
                            .when().get(ROOT_URI + "/{username}"); // Send POST request

            String resBody = response.getBody().asPrettyString();

            try {
                outputJSON.createNewFile();
                FileWriter writer = new FileWriter(outputJSON.getPath());
                writer.write(resBody);
                writer.close();
            } catch (IOException excp) {
                excp.printStackTrace();
            }
            response.then().body("id", equalTo(9021));
            response.then().body("username", equalTo("deepika03"));
            response.then().body("firstName", equalTo("Deepika"));
            response.then().body("lastName", equalTo("Basavaraju"));
            response.then().body("email", equalTo("deepika03@mail.com"));
            response.then().body("password", equalTo("password123"));
            response.then().body("phone", equalTo("9513313390"));
        }

        @Test(priority=3)
        public void deleteUser() throws IOException {
            Response response =
                    given().contentType(ContentType.JSON) 
                            .pathParam("username", "deepika03") 
                            .when().delete(ROOT_URI + "/{username}"); 
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("deepika03"));
        }
    }
