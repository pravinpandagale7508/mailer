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
import com.utcl.dto.ccnf.CcnfRMCPlantDetailsDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CcnfRMCPlantDetailsControllerIntegrationTest {
Gson gson = new Gson();
    
    static long newloiId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetCcnfRMCPlantDetailsId() {
        given()
            .when()
            .get("/api/rmcPlant/getCcnfRMCPlantDetailsById/"+newloiId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("plantid", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllCcnfRMCPlantDetailss() {
    	  Response resp=    given()
            .when()
            .get("/api/rmcPlant/getCcnfRMCPlantDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>loiList=	resp.jsonPath().getList("id");
		 log.info("CcnfRMCPlantDetailss"+loiList);
		for(int loiDetails : loiList)
		{
			log.info("loiDetails Details: {}",  loiDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddCcnfRMCPlantDetails() {
   	CcnfRMCPlantDetailsDto agencyDto= CcnfRMCPlantDetailsDto.builder()
      		  .id(0L)
      		  .plantid("ABC")
       		  .build();
     	  String addCcnfRMCPlantDetailsPayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addCcnfRMCPlantDetailsPayload)
            .post("/api/rmcPlant/addCcnfRMCPlantDetails")
            .then()
            .statusCode(200)
             .body("plantid", equalTo("ABC")).extract().response();
     	newloiId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestCcnfRMCPlantDetailsId {}", newloiId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateCcnfRMCPlantDetails() {
   	CcnfRMCPlantDetailsDto agencyDto= CcnfRMCPlantDetailsDto.builder()
      		  .id(newloiId)
      		  .plantid("PQR")
      		  .build();
     	   String addCcnfRMCPlantDetailsPayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addCcnfRMCPlantDetailsPayload)
            .put("/api/rmcPlant/updateCcnfRMCPlantDetails")
            .then()
            .statusCode(200)
             .body("plantid", equalTo("PQR")).extract().response();
    
    }
    
    

    
}



