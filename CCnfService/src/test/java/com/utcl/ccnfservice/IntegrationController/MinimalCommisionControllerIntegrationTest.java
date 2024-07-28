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
import com.utcl.dto.ccnf.MinimalCommisionDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MinimalCommisionControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private static Long latestminimalId = 0L;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	Gson gson = new Gson();

	@Test
	@Order(1)
	public void testaddMinimalCommisionCommission() {
		MinimalCommisionDto minimalCommisionDto = MinimalCommisionDto.builder().minimalId(0L).isMinimumCommission(true).commissiononMT(3500.0).build();

		String addMinimalCommissionPayload = gson.toJson(minimalCommisionDto);

		Response resp = given().when().contentType(ContentType.JSON).body(addMinimalCommissionPayload)
				.post("api/ccnfMinimalCommission/addCcnfMinimalCommission").then().statusCode(200)
				.contentType(ContentType.JSON).body("isMinimumCommission", equalTo(true)).body("commissiononMT", equalTo(3500.0f)).extract().response();

		latestminimalId = resp.jsonPath().getLong("minimalId");
		log.info("latestminimalId" + latestminimalId);
	}

	@Test
	@Order(2)
	public void testgetCcnfMinimalCommissionById() {
		log.info("latestDeuctionId+++++++++++" + latestminimalId);
		// Assuming vendor with ID 402 exists
		given().when().get("api/ccnfMinimalCommission/getCcnfMinimalCommissionById/" + latestminimalId).then()
				.statusCode(200).contentType(ContentType.JSON)// .body("deductionId", equalTo(latestDeuctionId))
				// .body("agentId", equalTo(1))
				.body("isMinimumCommission", equalTo(true)).body("commissiononMT", equalTo(3500.0f));
	}

	@Test
	@Order(3)
	public void testgetCcnfMinimalCommission() {
		Response resp = given().when().get("api/ccnfMinimalCommission/getCcnfMinimalCommission").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();
		
		List<Integer> allMinimalDetails = resp.jsonPath().getList("minimalId");
		
		log.info("minimalId Details" + allMinimalDetails);
		for (int minimalDetails : allMinimalDetails) {
			log.info("minimalDetails Details: " + minimalDetails);
		}
	}//getMinimalCommissionsByQuantity

	@Test
	@Order(4)
	public void getMinimalCommissionsByQuantityForMetroCity() {
		log.info("latestDeuctionId+++++++++++" + latestminimalId);
		double salesQauantity = 300.0;
		long loiId = 14; 
		Boolean minimalCommisionFlg=true;// Assuming slabAmount and loiId
		given().when().get("api/ccnfMinimalCommission/getMinimalCommisionByQuantity/"+salesQauantity+"/"+loiId+"/"+minimalCommisionFlg).then()
				.statusCode(200).contentType(ContentType.JSON);
	}
	
	@Test
	@Order(5)
	public void getMinimalCommissionsByQuantity() {
		log.info("latestDeuctionId+++++++++++" + latestminimalId);
		double salesQauantity = 300.0;
		long loiId = 10; 
		Boolean minimalCommisionFlg=false;// Assuming slabAmount and loiId
		given().when().get("api/ccnfMinimalCommission/getMinimalCommisionByQuantity/"+salesQauantity+"/"+loiId+"/"+minimalCommisionFlg).then()
				.statusCode(200).contentType(ContentType.JSON);
	}
	
	
	@Test
	@Order(6)
	public void testdeleteCCnfLoiDetailsById() {
		// Assuming vendor with ID 352 exists
		given().when().delete("api/ccnfMinimalCommission/deleteCcnfMinimalCommisionById/352").then().statusCode(200);
	}
}
