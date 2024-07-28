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
import com.utcl.dto.ccnf.RegionDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class RegionControllerIntegrationTest {
    Gson gson = new Gson();
    
    static long newregionId = 0L;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(2)
     void testGetRegionByRegionId() {
    	log.info("print region id {}"+newregionId);
        given()
            .when()
            .get("/api/region/getRegionById/"+newregionId)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("regionName", equalTo("ABC"))
            .body("createdBy", equalTo("Varsga1"))
            .body("updatedBy", equalTo("Varsga1"));
    }
   
    @Test
    @Order(3)
     void testGetAllRegions() {
    	  Response resp=    given()
            .when()
            .get("/api/region/getRegionDetails")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().response();
        List<Integer>regionList=	resp.jsonPath().getList("regionId");
		 log.info("Regions"+regionList);
		for(int regionDetails : regionList)
		{
			log.info("regionDetails Details: {}",  regionDetails);
		}
    }
    
   
    @Test
    @Order(1)
     void testAddRegion() {
   	RegionDto regionDto= RegionDto.builder()
      		  .regionId(0L)
      		  .regionName("ABC")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	  String addRegionPayload=gson.toJson(regionDto);
     	 var mockResponse =   given().when()
            .contentType(ContentType.JSON).body(addRegionPayload)
            .post("/api/region/addRegion")
            .then()
            .statusCode(200)
             .body("regionName", equalTo("ABC")).extract().response();
     	newregionId = Integer.parseInt(mockResponse.jsonPath().get("regionId").toString());
		log.info("latestregion {}", newregionId);
    
    }
    
    @Test
    @Order(4)
     void testUpdateRegion() {
   	RegionDto regionDto= RegionDto.builder()
      		  .regionId(newregionId)
      		  .regionName("PQR")
      		  .createdBy("Varsga1")
      		  .updatedBy("Varsga1")
      		  .build();
     	   String addRegionPayload=gson.toJson(regionDto);
           given().when()
            .contentType(ContentType.JSON).body(addRegionPayload)
            .put("/api/region/updateRegion")
            .then()
            .statusCode(200)
             .body("regionName", equalTo("PQR")).extract().response();
    
    }
    
    

    
}

