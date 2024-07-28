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
import com.utcl.dto.ccnf.I2TalukaDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class I2TalukaControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newi2talukaId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetI2TalukaByI2TalukaId() {
        given()
            .when()
            .get("/api/i2Taluka/getI2TalukaById/"+newi2talukaId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("talukaName", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
     void testGetAllI2Talukas() {
    	  Response resp=    given()
            .when()
            .get("/api/i2Taluka/getI2TalukaDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>i2talukaList=	resp.jsonPath().getList("id");
		 log.info("I2Talukas"+i2talukaList);
		for(int i2talukaDetails : i2talukaList)
		{
			log.info("i2talukaDetails Details: {}",  i2talukaDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddI2Taluka() {
   	I2TalukaDto agencyDto= I2TalukaDto.builder()
      		  .id(0L)
      		  .talukaName("ABC")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	  String addI2TalukaPayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addI2TalukaPayload)
            .post("/api/i2Taluka/addI2Taluka")
            .then()
            .statusCode(200)
             .body("talukaName", equalTo("ABC")).extract().response();
     	newi2talukaId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("newi2talukaId {}", newi2talukaId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateI2Taluka() {
   	I2TalukaDto agencyDto= I2TalukaDto.builder()
      		  .id(newi2talukaId)
      		  .talukaName("PQR")
      		  .build();
     	   String addI2TalukaPayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addI2TalukaPayload)
            .put("/api/i2Taluka/updateI2Taluka")
            .then()
            .statusCode(200)
             .body("talukaName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

