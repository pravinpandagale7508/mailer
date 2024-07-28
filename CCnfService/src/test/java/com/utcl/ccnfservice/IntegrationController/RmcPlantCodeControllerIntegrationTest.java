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
import com.utcl.dto.ccnf.RmcPlantCodeDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class RmcPlantCodeControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newplantId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetRMCPlantCodeById() {
        given()
            .when()
            .get("/api/rmcPlantCode/getRmcPlantCodeById/"+newplantId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("rmcCode", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
     void testGetAllRMCPlants() {
    	  Response resp=    given()
            .when()
            .get("/api/rmcPlantCode/getRmcPlantCodeDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>rmcPlantList=	resp.jsonPath().getList("id");
		 log.info("plants"+rmcPlantList);
		for(int plantDetails : rmcPlantList)
		{
			log.info("plantDetails Details: {}",  plantDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddRMCPlantCode() {
   	RmcPlantCodeDto agencyDto= RmcPlantCodeDto.builder()
      		  .id(0L)
      		  .rmcCode("ABC")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	  String addRmcPlantCodePayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addRmcPlantCodePayload)
            .post("/api/rmcPlantCode/addRmcPlantCode")
            .then()
            .statusCode(200)
             .body("rmcCode", equalTo("ABC")).extract().response();
     	newplantId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestId {}", newplantId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateRmcPlantCode() {
   	RmcPlantCodeDto agencyDto= RmcPlantCodeDto.builder()
      		  .id(newplantId)
      		  .rmcCode("PQR")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	   String addRmcPlantCodePayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addRmcPlantCodePayload)
            .put("/api/rmcPlantCode/updateRmcPlantCode")
            .then()
            .statusCode(200)
             .body("rmcCode", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

