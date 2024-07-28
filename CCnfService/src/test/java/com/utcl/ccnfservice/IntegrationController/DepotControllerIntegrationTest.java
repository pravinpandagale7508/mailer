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
import com.utcl.dto.ccnf.DepotDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DepotControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newdepotId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetDepotByDepotId() {
    	log.info("print depo id {}"+newdepotId);
        given()
            .when()
            .get("/api/depot/getDepotDetailsById/"+newdepotId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("depotName", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
     void testGetAllDepots() {
    	  Response resp=    given()
            .when()
            .get("/api/depot/getDepotDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>depotList=	resp.jsonPath().getList("depotId");
		 log.info("Depots"+depotList);
		for(int depotDetails : depotList)
		{
			log.info("depotDetails Details: {}",  depotDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddDepot() {
   	DepotDto agencyDto= DepotDto.builder()
      		  .depotId(0L)
      		  .depotName("ABC")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	  String addDepotPayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addDepotPayload)
            .post("/api/depot/addDepot")
            .then()
            .statusCode(200)
             .body("depotName", equalTo("ABC")).extract().response();
     	newdepotId = Integer.parseInt(mockResponse.jsonPath().get("depotId").toString());
		log.info("latestAgentId {}", newdepotId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateDepot() {
   	DepotDto agencyDto= DepotDto.builder()
      		  .depotId(newdepotId)
      		  .depotName("PQR")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	   String addDepotPayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addDepotPayload)
            .put("/api/depot/updateDepot")
            .then()
            .statusCode(200)
             .body("depotName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

