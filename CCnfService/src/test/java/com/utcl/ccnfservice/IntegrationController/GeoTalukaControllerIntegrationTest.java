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
import com.utcl.dto.ccnf.GeoTalukaDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GeoTalukaControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newgeoTalukaId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetGeoTalukaById() {
        given()
            .when()
            .get("/api/geoTaluka/getGeoTalukaById/"+newgeoTalukaId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("talukaName", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllGeoTalukas() {
    	  Response resp=    given()
            .when()
            .get("/api/geoTaluka/getGeoTalukaDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>geoTalukaList=	resp.jsonPath().getList("id");
		 log.info("GeoTalukas"+geoTalukaList);
		for(int geoTalukaDetails : geoTalukaList)
		{
			log.info("geoTalukaDetails Details: {}",  geoTalukaDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddGeoTaluka() {
   	GeoTalukaDto geoTalukaDto= GeoTalukaDto.builder()
      		  .id(0L)
      		  .talukaName("ABC")
       		  .build();
     	  String addGeoTalukaPayload=gson.toJson(geoTalukaDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addGeoTalukaPayload)
            .post("/api/geoTaluka/addGeoTaluka")
            .then()
            .statusCode(200)
             .body("talukaName", equalTo("ABC")).extract().response();
     	newgeoTalukaId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestGeoTalukaId {}", newgeoTalukaId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateGeoTaluka() {
   	GeoTalukaDto geoTalukaDto= GeoTalukaDto.builder()
      		  .id(newgeoTalukaId)
      		  .talukaName("PQR")
      		  .build();
     	   String addGeoTalukaPayload=gson.toJson(geoTalukaDto);
           given().when()
            .contentType(ContentType.JSON).body(addGeoTalukaPayload)
            .put("/api/geoTaluka/updateGeoTaluka")
            .then()
            .statusCode(200)
             .body("talukaName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

