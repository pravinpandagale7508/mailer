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
import com.utcl.dto.ccnf.GeoDistDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GeoDistControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newgeoDistId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetGeoDistById() {
        given()
            .when()
            .get("/api/geoDist/getGeoDistById/"+newgeoDistId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("distName", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllGeoDists() {
    	  Response resp=    given()
            .when()
            .get("/api/geoDist/getGeoDistDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>geoDistList=	resp.jsonPath().getList("id");
		 log.info("GeoDists"+geoDistList);
		for(int geoDistDetails : geoDistList)
		{
			log.info("geoDistDetails Details: {}",  geoDistDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddGeoDist() {
   	GeoDistDto geoDistDto= GeoDistDto.builder()
      		  .id(0L)
      		  .distName("ABC")
       		  .build();
     	  String addGeoDistPayload=gson.toJson(geoDistDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addGeoDistPayload)
            .post("/api/geoDist/addGeoDist")
            .then()
            .statusCode(200)
             .body("distName", equalTo("ABC")).extract().response();
     	newgeoDistId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestGeoDistId {}", newgeoDistId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateGeoDist() {
   	GeoDistDto geoDistDto= GeoDistDto.builder()
      		  .id(newgeoDistId)
      		  .distName("PQR")
      		  .build();
     	   String addGeoDistPayload=gson.toJson(geoDistDto);
           given().when()
            .contentType(ContentType.JSON).body(addGeoDistPayload)
            .put("/api/geoDist/updateGeoDist")
            .then()
            .statusCode(200)
             .body("distName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

