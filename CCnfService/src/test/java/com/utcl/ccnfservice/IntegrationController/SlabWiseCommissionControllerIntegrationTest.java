package com.utcl.ccnfservice.IntegrationController;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;
import com.utcl.dto.ccnf.MinimalCommisionDto;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SlabWiseCommissionControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private static Long latestSlabWiseCommissionId = 0L;

	List<SlabwiseCommisionDto> slabWiseCommissionList = new ArrayList<>();
	List<DeductiononDirectSalesDto> deductiononDirectSalesDtos = new ArrayList<>();
	List<MinimalCommisionDto> minimalCommisionDtos = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	Gson gson = new Gson();

	@Test
	@Order(1)
	public void testaddCcnfSlabwiseCommission() {
		SlabwiseCommisionDto slabWiseComissionDto = SlabwiseCommisionDto.builder().slabId(0L).minAmount(0.0)
				.maxAmount(300.0).build();

		String slabwisePayload = gson.toJson(slabWiseComissionDto);

		Response resp = given().when().contentType(ContentType.JSON).body(slabwisePayload)
				.post("api/ccnfSlabWise/addCcnfSlabwiseCommission").then().statusCode(200).contentType(ContentType.JSON)
				.body("minAmount", equalTo(0.0f)).body("maxAmount", equalTo(300.0f)).extract().response();

		latestSlabWiseCommissionId = resp.jsonPath().getLong("slabId");
		log.info("latestDeuctionId" + latestSlabWiseCommissionId);
	}

	@Test
	@Order(2)
	public void testgetCcnfSlabwiseCommissionsByid() {
		log.info("latestDeuctionId+++++++++++" + latestSlabWiseCommissionId);
		// Assuming vendor with ID 402 exists
		given().when().get("api/ccnfSlabWise/getCcnfSlabwiseCommissionsByid/" + latestSlabWiseCommissionId).then()
				.statusCode(200).contentType(ContentType.JSON).body("minAmount", equalTo(0.0f)).body("maxAmount", equalTo(300.0f));
	}

	@Test
	@Order(3)
	public void testgetCcnfSlabwiseCommissions() throws JsonMappingException, JsonProcessingException {
		Response resp = given().when().get("api/ccnfSlabWise/getCcnfSlabwiseCommissions").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();
		List<Integer> allSlabWiseDetails = resp.jsonPath().getList("slabId");
		log.info("SlabWiseDetails Details" + allSlabWiseDetails);
		for (int slabDetails : allSlabWiseDetails) {
			log.info("Slab Details: " + slabDetails);
		}
	}

	@Test
	@Order(4)
	public void getCcnfSlabwiseCommissionsByQuantity() {
		log.info("latestDeuctionId+++++++++++" + latestSlabWiseCommissionId);
		Double slabAmount = 7500.0;
		long loiId = 10; // Assuming slabAmount and loiId
		given().when().get("api/ccnfSlabWise/getCcnfSlabwiseCommissionsByQuantity/"+slabAmount+"/"+loiId).then()
				.statusCode(200).contentType(ContentType.JSON).equals(26500.0);
	}
	
	@Test
	@Order(5)
	public void getCcnfSlabwiseCommissionsByQuantityForMetroCity() {
		log.info("latestDeuctionId+++++++++++" + latestSlabWiseCommissionId);
		Double slabAmount = 77722.79;
		long loiId = 14; // Assuming slabAmount and loiId
		given().when().get("api/ccnfSlabWise/getCcnfSlabwiseCommissionsByQuantity/"+slabAmount+"/"+loiId).then()
				.statusCode(200).contentType(ContentType.JSON).assertThat().equals(1721401.4);
	}

	@Test
	@Order(6)
	public void testdeleteCcnfSlabwiseCommissionById() {
		// Assuming vendor with ID 352 exists
		given().when().delete("api/ccnfSlabWise/deleteCcnfSlabwiseCommissionById/" + latestSlabWiseCommissionId).then()
				.statusCode(200);
	}

}
