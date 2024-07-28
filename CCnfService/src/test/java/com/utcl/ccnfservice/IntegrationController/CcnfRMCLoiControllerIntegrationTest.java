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
import com.utcl.dto.ccnf.CcnfRMCLoiDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CcnfRMCLoiControllerIntegrationTest {
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
     void testGetCcnfRMCExpenseId() {
        given()
            .when()
            .get("/api/rmcLoi/getCcnfRMCLoiById/"+newloiId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("billingCycleType", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllCcnfRMCExpenses() {
    	  Response resp=    given()
            .when()
            .get("/api/rmcLoi/getCcnfRMCLoiDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>loiList=	resp.jsonPath().getList("id");
		 log.info("CcnfRMCExpenses"+loiList);
		for(int loiDetails : loiList)
		{
			log.info("loiDetails Details: {}",  loiDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddCcnfRMCExpense() {
   	CcnfRMCLoiDto agencyDto= CcnfRMCLoiDto.builder()
      		  .id(0L)
      		  .billingCycleType("ABC")
       		  .build();
     	  String addCcnfRMCExpensePayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addCcnfRMCExpensePayload)
            .post("/api/rmcLoi/addCcnfRMCLoi")
            .then()
            .statusCode(200)
             .body("billingCycleType", equalTo("ABC")).extract().response();
     	newloiId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestCcnfRMCExpenseId {}", newloiId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateCcnfRMCExpense() {
   	CcnfRMCLoiDto agencyDto= CcnfRMCLoiDto.builder()
      		  .id(newloiId)
      		  .billingCycleType("PQR")
      		  .build();
     	   String addCcnfRMCExpensePayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addCcnfRMCExpensePayload)
            .put("/api/rmcLoi/updateCcnfRMCLoi")
            .then()
            .statusCode(200)
             .body("billingCycleType", equalTo("PQR")).extract().response();
    
    }
    
    

    
}



