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
import com.utcl.dto.ccnf.CityDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CityControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newcityId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetCityId() {
        given()
            .when()
            .get("/api/city/getCityById/"+newcityId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("cityName", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllCitys() {
    	  Response resp=    given()
            .when()
            .get("/api/city/getCityDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>cityList=	resp.jsonPath().getList("id");
		 log.info("Citys"+cityList);
		for(int cityDetails : cityList)
		{
			log.info("cityDetails Details: {}",  cityDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddCity() {
   	CityDto agencyDto= CityDto.builder()
      		  .id(0L)
      		  .cityName("ABC")
       		  .build();
     	  String addCityPayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addCityPayload)
            .post("/api/city/addCity")
            .then()
            .statusCode(200)
             .body("cityName", equalTo("ABC")).extract().response();
     	newcityId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestCityId {}", newcityId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateCity() {
   	CityDto agencyDto= CityDto.builder()
      		  .id(newcityId)
      		  .cityName("PQR")
      		  .build();
     	   String addCityPayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addCityPayload)
            .put("/api/city/updateCity")
            .then()
            .statusCode(200)
             .body("cityName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

