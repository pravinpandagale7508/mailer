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
import com.utcl.dto.ccnf.StateDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class StateControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newstateId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetStateByStateId() {
        given()
            .when()
            .get("/api/state/getStateById/"+newstateId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("stateCode", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllStates() {
    	  Response resp=    given()
            .when()
            .get("/api/state/getStateDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>stateList=	resp.jsonPath().getList("id");
		 log.info("States"+stateList);
		for(int stateDetails : stateList)
		{
			log.info("stateDetails Details: {}",  stateDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddState() {
   	StateDto agencyDto= StateDto.builder()
      		  .id(0L)
      		  .stateCode("ABC")
       		  .build();
     	  String addStatePayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addStatePayload)
            .post("/api/state/addState")
            .then()
            .statusCode(200)
             .body("stateCode", equalTo("ABC")).extract().response();
     	newstateId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestStateId {}", newstateId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateState() {
   	StateDto agencyDto= StateDto.builder()
      		  .id(newstateId)
      		  .stateCode("PQR")
      		  .build();
     	   String addStatePayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addStatePayload)
            .put("/api/state/updateState")
            .then()
            .statusCode(200)
             .body("stateCode", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

