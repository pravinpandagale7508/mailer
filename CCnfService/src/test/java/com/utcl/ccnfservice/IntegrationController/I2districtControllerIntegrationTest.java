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
import com.utcl.dto.ccnf.I2districtDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class I2districtControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newi2districtId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetI2districtByI2districtId() {
        given()
            .when()
            .get("/api/i2district/getI2districtById/"+newi2districtId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("distName", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
     void testGetAllI2districts() {
    	  Response resp=    given()
            .when()
            .get("/api/i2district/getI2districtDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>i2districtList=	resp.jsonPath().getList("distId");
		 log.info("I2districts"+i2districtList);
		for(int i2districtDetails : i2districtList)
		{
			log.info("i2districtDetails Details: {}",  i2districtDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddI2district() {
   	I2districtDto agencyDto= I2districtDto.builder()
      		  .distId(0L)
      		  .distName("ABC")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	  String addI2districtPayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addI2districtPayload)
            .post("/api/i2district/addI2district")
            .then()
            .statusCode(200)
             .body("distName", equalTo("ABC")).extract().response();
     	newi2districtId = Integer.parseInt(mockResponse.jsonPath().get("distId").toString());
		log.info("newi2districtId {}", newi2districtId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateI2district() {
   	I2districtDto agencyDto= I2districtDto.builder()
      		  .distId(newi2districtId)
      		  .distName("PQR")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	   String addI2districtPayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addI2districtPayload)
            .put("/api/i2district/updateI2district")
            .then()
            .statusCode(200)
             .body("distName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

