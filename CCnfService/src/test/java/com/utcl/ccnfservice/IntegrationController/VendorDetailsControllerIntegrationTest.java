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
import com.utcl.dto.godown.VendorDetailsDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class VendorDetailsControllerIntegrationTest {

    @LocalServerPort
    private int port;

    Gson gson = new Gson();
    static long newvendId = 0L;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

   @Test
   @Order(2)
    public void testGetVendorDetailsById() {
        given()
            .when()
            .get("/api/vendorDetails/getVendorDetailsById/"+newvendId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("firstName", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
    public void testGetAllVendorDetails() {
    	  Response resp=    given()
            .when()
            .get("/api/vendorDetails/getAll")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
    	  
    	         
        List<Integer>agenciesList=	resp.jsonPath().getList("id");
		for(int agentDetails : agenciesList)
		{
			log.info("bank Details: " + agentDetails);
		}
    }
    
   
    @Test
    @Order(1)
    public void testAddVendorDetails() {
      VendorDetailsDto vendorDetailsDto= VendorDetailsDto.builder()
    		  .id(0L)
    		  .firstName("ABC")
    		  .createdBy("Varsga1")
    		  .updatedBy("Varsga1")
    		  .build();
   	  String addVendorDetailsPayload=gson.toJson(vendorDetailsDto);
   	
      var mockResponse=given().when()
            .contentType(ContentType.JSON).body(addVendorDetailsPayload)
            .post("/api/vendorDetails/add")
            .then()
            .statusCode(200)
            .body("firstName", equalTo("ABC")).extract().response();
      newvendId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
  	log.info("newvendId {}", newvendId);
       
    }
    
    @Test
    @Order(4)
    public void testUpdateVendorDetails() {
      VendorDetailsDto vendorDetailsDto= VendorDetailsDto.builder()
    		  .id(newvendId)
    		  .firstName("PQR")
    		  .createdBy("Varsga1")
    		  .updatedBy("Varsga1")
    		  .build();
   	  String addVendorDetailsPayload=gson.toJson(vendorDetailsDto);
   	
      given().when()
            .contentType(ContentType.JSON).body(addVendorDetailsPayload)
            .put("/api/vendorDetails/update")
            .then()
            .statusCode(200)
            .body("firstName", equalTo("PQR")).extract().response();
        
       
    }

    
}



