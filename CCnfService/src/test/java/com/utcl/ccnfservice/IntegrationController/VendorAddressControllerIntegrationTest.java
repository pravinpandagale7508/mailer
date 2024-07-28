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
import com.utcl.dto.godown.VendorAddressDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class VendorAddressControllerIntegrationTest {

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
    public void testGetVendorAddressById() {
        given()
            .when()
            .get("/api/vendorAddress/getVendorAddressById/"+newvendId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("city", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
    public void testGetAllVendorAddress() {
    	  Response resp=    given()
            .when()
            .get("/api/vendorAddress/getAll")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
    	  
    	         
        List<Integer>agenciesList=	resp.jsonPath().getList("id");
		for(int agentDetails : agenciesList)
		{
			log.info("vendorAddress Details: " + agentDetails);
		}
    }
    
   
    @Test
    @Order(1)
    public void testAddVendorAddress() {
     	
      VendorAddressDto vendorAddressDto= VendorAddressDto.builder()
    		  .id(0L)
    		  .city("ABC")
    		  .createdBy("Varsga1")
    		  .updatedBy("Varsga1")
    		  .build();
   	  String addVendorAddressPayload=gson.toJson(vendorAddressDto);
   	
    var mockResponse=  given().when()
            .contentType(ContentType.JSON).body(addVendorAddressPayload)
            .post("/api/vendorAddress/add")
            .then()
            .statusCode(200)
             .body("city", equalTo("ABC")).extract().response();
    newvendId = Integer.parseInt(mockResponse.jsonPath().get("id").toString());
    	log.info("newvendId {}", newvendId);
            
       
    }
    
    @Test
    @Order(4)
    public void testUpdateVendorAddress() {
      VendorAddressDto vendorAddressDto= VendorAddressDto.builder()
    		  .id(newvendId)
    		  .city("PQR")
    		  .createdBy("Varsga1")
    		  .updatedBy("Varsga1")
    		  .build();
   	  String addVendorAddressPayload=gson.toJson(vendorAddressDto);
   	
      given().when()
            .contentType(ContentType.JSON).body(addVendorAddressPayload)
            .put("/api/vendorAddress/update")
            .then()
            .statusCode(200)
             .body("city", equalTo("PQR")).extract().response();
        
       
    }
    
    

    
}



