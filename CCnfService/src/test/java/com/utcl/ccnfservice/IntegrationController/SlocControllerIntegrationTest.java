package com.utcl.ccnfservice.IntegrationController;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import com.google.gson.Gson;
import com.utcl.dto.ccnf.SlocDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SlocControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newslocId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetSlocBySlocId() {
    	log.info("print sloc id {}"+newslocId);
        given()
            .when()
            .get("/api/sloc/getSlocById/"+newslocId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("slocName", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
     void testGetAllSlocs() {
    	  Response resp=    given()
            .when()
            .get("/api/sloc/getSlocDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>slocList=	resp.jsonPath().getList("slocId");
		 log.info("Slocs"+slocList);
		for(int slocDetails : slocList)
		{
			log.info("slocDetails Details: {}",  slocDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddSloc() {
   	SlocDto slocDto= SlocDto.builder()
      		  .slocId(0L)
      		  .slocName("ABC")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	  String addSlocPayload=gson.toJson(slocDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addSlocPayload)
            .post("/api/sloc/addSloc")
            .then()
            .statusCode(200)
             .body("slocName", equalTo("ABC")).extract().response();
     	newslocId = Integer.parseInt(mockResponse.jsonPath().get("slocId").toString());
		log.info("latestsloc {}", newslocId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateSloc() {
   	SlocDto slocDto= SlocDto.builder()
      		  .slocId(newslocId)
      		  .slocName("PQR")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	   String addSlocPayload=gson.toJson(slocDto);
           given().when()
            .contentType(ContentType.JSON).body(addSlocPayload)
            .put("/api/sloc/updateSloc")
            .then()
            .statusCode(200)
             .body("slocName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

