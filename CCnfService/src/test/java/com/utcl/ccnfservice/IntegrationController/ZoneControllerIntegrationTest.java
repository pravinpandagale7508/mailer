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
import com.utcl.dto.ccnf.ZoneDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ZoneControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newzoneId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetZoneById() {
        given()
            .when()
            .get("/api/zone/getZoneById/"+newzoneId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("zoneCode", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllZones() {
    	  Response resp=    given()
            .when()
            .get("/api/zone/getZoneDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>zoneList=	resp.jsonPath().getList("id");
		 log.info("Zones"+zoneList);
		for(int zoneDetails : zoneList)
		{
			log.info("zoneDetails Details: {}",  zoneDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddZone() {
   	ZoneDto zoneDto= ZoneDto.builder()
      		  .id(0L)
      		  .zoneCode("ABC")
       		  .build();
     	  String addZonePayload=gson.toJson(zoneDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addZonePayload)
            .post("/api/zone/addZone")
            .then()
            .statusCode(200)
             .body("zoneCode", equalTo("ABC")).extract().response();
     	newzoneId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestZoneId {}", newzoneId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateZone() {
   	ZoneDto zoneDto= ZoneDto.builder()
      		  .id(newzoneId)
      		  .zoneCode("PQR")
      		  .build();
     	   String addZonePayload=gson.toJson(zoneDto);
           given().when()
            .contentType(ContentType.JSON).body(addZonePayload)
            .put("/api/zone/updateZone")
            .then()
            .statusCode(200)
             .body("zoneCode", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

