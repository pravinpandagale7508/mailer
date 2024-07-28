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
import com.utcl.dto.ccnf.AgencyDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AgencyControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newagentId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetAgencyByAgencyId() {
        given()
            .when()
            .get("/api/agent/getAgentDetailsByAgentId/"+newagentId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("agentName", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
     void testGetAllAgencies() {
    	  Response resp=    given()
            .when()
            .get("/api/agent/getAgencyDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>agenciesList=	resp.jsonPath().getList("agentId");
		 log.info("Agencies"+agenciesList);
		for(int agentDetails : agenciesList)
		{
			log.info("agentDetails Details: {}",  agentDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddAgency() {
   	AgencyDto agencyDto= AgencyDto.builder()
      		  .agentId(0L)
      		  .agentName("ABC")
      		  .build();
     	  String addAgencyPayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addAgencyPayload)
            .post("/api/agent/addAgency")
            .then()
            .statusCode(200)
             .body("agentName", equalTo("ABC")).extract().response();
     	newagentId = Integer.parseInt(mockResponse.jsonPath().get("agentId").toString());
		log.info("latestAgentId {}", newagentId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateAgency() {
   	AgencyDto agencyDto= AgencyDto.builder()
      		  .agentId(newagentId)
      		  .agentName("PQR")
       		  .build();
     	   String addAgencyPayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addAgencyPayload)
            .put("/api/agent/updateAgency")
            .then()
            .statusCode(200)
             .body("agentName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

