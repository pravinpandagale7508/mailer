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
import com.utcl.dto.ccnf.GubtPlantCodeDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GubPlantCodeControllerIntegrationTest {
 
	 Gson gson = new Gson();
    @LocalServerPort
    private int port;

    static long newgubId = 0L;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
    public void testGubById() {
        given()
            .when()
            .get("/api/gubPlant/getGubPlantCodeDetailsById/"+newgubId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("gubCode", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
    
    @Test
    @Order(3)
    public void testGetAllGubPlantCode() {
    	Response resp=   given()
            .when()
            .get("/api/gubPlant/getGubPlantDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>allGubPlant=	resp.jsonPath().getList("gubId");
		 log.info("gubcode"+allGubPlant);
		for(int gubDetails : allGubPlant)
		{
			log.info("allGubPlant Details: " + gubDetails);
		}
    }
    
    @Test
    @Order(1)
    public void testAddGub() {
      GubtPlantCodeDto gubtPlantCodeDto= GubtPlantCodeDto.builder()
    		  .gubId(0L)
    		  .gubCode("ABC")
    		  .createdBy("Varsga1")
    		  .updatedBy("Varsga1")
    		  .build();
   	  String addPayload=gson.toJson(gubtPlantCodeDto);
   	
   	 var mockResponse =    given().when()
            .contentType(ContentType.JSON).body(addPayload)
            .post("/api/gubPlant/addGubPlantCode")
            .then()
            .statusCode(200)
            .body("gubCode", equalTo("ABC")).extract().response();
        newgubId = Integer.parseInt(mockResponse.jsonPath().get("gubId").toString());
		log.info("newgubId {}", newgubId);
    }
    
    @Test
    @Order(4)
    public void testUpdateGub() {
      GubtPlantCodeDto gubtPlantCodeDto= GubtPlantCodeDto.builder()
    		  .gubId(newgubId)
    		  .gubCode("PQR")
    		  .createdBy("Varsga1")
    		  .updatedBy("Varsga1")
    		  .build();
   	  String updatePayload=gson.toJson(gubtPlantCodeDto);
   	
   	 given().when()
            .contentType(ContentType.JSON).body(updatePayload)
            .put("/api/gubPlant/updateGubPlantCode")
            .then()
            .statusCode(200)
            .body("gubCode", equalTo("PQR")).extract().response();

    }
    
    
    

    

}

