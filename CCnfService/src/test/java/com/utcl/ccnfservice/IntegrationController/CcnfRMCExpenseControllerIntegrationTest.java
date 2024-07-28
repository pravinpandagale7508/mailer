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
import com.utcl.dto.ccnf.CcnfRMCExpenseDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CcnfRMCExpenseControllerIntegrationTest {
Gson gson = new Gson();
    
    static long newexpenseId = 0L;

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
            .get("/api/rmcExpense/getCcnfRMCExpenseById/"+newexpenseId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("type", equalTo("ABC"));
    }
   
    @Test
    @Order(3)
     void testGetAllCcnfRMCExpenses() {
    	  Response resp=    given()
            .when()
            .get("/api/rmcExpense/getCcnfRMCExpenseDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>expenseList=	resp.jsonPath().getList("id");
		 log.info("CcnfRMCExpenses"+expenseList);
		for(int expenseDetails : expenseList)
		{
			log.info("expenseDetails Details: {}",  expenseDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddCcnfRMCExpense() {
   	CcnfRMCExpenseDto agencyDto= CcnfRMCExpenseDto.builder()
      		  .id(0L)
      		  .type("ABC")
       		  .build();
     	  String addCcnfRMCExpensePayload=gson.toJson(agencyDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addCcnfRMCExpensePayload)
            .post("/api/rmcExpense/addCcnfRMCExpense")
            .then()
            .statusCode(200)
             .body("type", equalTo("ABC")).extract().response();
     	newexpenseId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
		log.info("latestCcnfRMCExpenseId {}", newexpenseId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateCcnfRMCExpense() {
   	CcnfRMCExpenseDto agencyDto= CcnfRMCExpenseDto.builder()
      		  .id(newexpenseId)
      		  .type("PQR")
      		  .build();
     	   String addCcnfRMCExpensePayload=gson.toJson(agencyDto);
           given().when()
            .contentType(ContentType.JSON).body(addCcnfRMCExpensePayload)
            .put("/api/rmcExpense/updateCcnfRMCExpense")
            .then()
            .statusCode(200)
             .body("type", equalTo("PQR")).extract().response();
    
    }
    
    

    
}



