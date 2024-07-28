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
import com.utcl.dto.ccnf.CountyDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CountyControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newcountyId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetCountyId() {
        given()
            .when()
            .get("/api/county/getCountyById/"+newcountyId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("countyName", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllCountys() {
    	  Response resp=    given()
            .when()
            .get("/api/county/getCountyDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>countyList=	resp.jsonPath().getList("id");
		 log.info("Countys"+countyList);
		for(int countyDetails : countyList)
		{
			log.info("countyDetails Details: {}",  countyDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddCounty() {
   	CountyDto agencyDto= CountyDto.builder()
      		  .id(0L)
      		  .countyName("ABC")
       		  .build();
     	  String addCountyPayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addCountyPayload)
            .post("/api/county/addCounty")
            .then()
            .statusCode(200)
             .body("countyName", equalTo("ABC")).extract().response();
     	newcountyId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestCountyId {}", newcountyId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateCounty() {
   	CountyDto agencyDto= CountyDto.builder()
      		  .id(newcountyId)
      		  .countyName("PQR")
      		  .build();
     	   String addCountyPayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addCountyPayload)
            .put("/api/county/updateCounty")
            .then()
            .statusCode(200)
             .body("countyName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

