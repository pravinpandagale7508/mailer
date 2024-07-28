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
import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSalesDetails;
import com.utcl.dto.ccnf.CcnfRMCSlabwiseCommisionDto;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;
import com.utcl.dto.ccnf.MinimalCommisionDto;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;
import com.utcl.dto.ccnf.requestResponce.RmcSalesQuantity;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RMCSlabWiseCommissionControllerIntegrationTest {
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
		CcnfRMCSlabwiseCommisionDto ccnfRmcSlabwiseCommisionDto = CcnfRMCSlabwiseCommisionDto.builder().slabId(0L).minAmount(0.0)
				.maxAmount(300.0).build();

		String rmcSlabwisePayload = gson.toJson(ccnfRmcSlabwiseCommisionDto);

		Response resp = given().when().contentType(ContentType.JSON).body(rmcSlabwisePayload)
				.post("api/ccnfRMCCommission/addRMCSlabwiseCommission").then().statusCode(200).contentType(ContentType.JSON)
				.body("minAmount", equalTo(0.0f)).body("maxAmount", equalTo(300.0f)).extract().response();

		latestSlabWiseCommissionId = resp.jsonPath().getLong("slabId");
		log.info("latestDeuctionId" + latestSlabWiseCommissionId);
	}

	@Test
	@Order(2)
	public void testgetCcnfSlabwiseCommissionsByid() {
		log.info("latestDeuctionId+++++++++++" + latestSlabWiseCommissionId);
		// Assuming vendor with ID 402 exists
		given().when().get("api/ccnfRMCCommission/getCcnfSlabwiseCommissionsByid/" + latestSlabWiseCommissionId).then()
				.statusCode(200).contentType(ContentType.JSON).body("minAmount", equalTo(0.0f)).body("maxAmount", equalTo(300.0f));
	}

	@Test
	@Order(3)
	public void testgetCcnfSlabwiseCommissions() throws JsonMappingException, JsonProcessingException {
		Response resp = given().when().get("api/ccnfRMCCommission//getCcnfSlabwiseCommissions").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();
		List<Integer> allSlabWiseDetails = resp.jsonPath().getList("slabId");
		log.info("SlabWiseDetails Details" + allSlabWiseDetails);
		for (int slabDetails : allSlabWiseDetails) {
			log.info("Slab Details: " + slabDetails);
		}
	}

	@Test
	@Order(4)
	public void getRmcTotalSalesVariableAmount() {
		log.info("latestDeuctionId+++++++++++" + latestSlabWiseCommissionId);
		List<String> plantCodes=new ArrayList<>();
		plantCodes.add("261");
		plantCodes.add("262");
		List<Double> wastage=new ArrayList<>();
		wastage.add(24.70);
		RmcSalesQuantity rmcSalesQuantity = RmcSalesQuantity.builder().plants(plantCodes).wastage(wastage).month(7).year(2024).build();
		String rmcSlabwisePayload = gson.toJson(rmcSalesQuantity);
		long loiId = 4; // Assuming loiId
		Response resp= given().when().contentType(ContentType.JSON).body(rmcSlabwisePayload)
				.post("api/ccnfRMCCommission/getRmcTotalSalesVariableAmount?loiId=4")
		.then().statusCode(200).contentType(ContentType.JSON).extract().response();
		log.info(resp.asPrettyString());
	}

	@Test
	@Order(6)
	public void testdeleteCcnfSlabwiseCommissionById() {
		// Assuming vendor with ID 352 exists
		given().when().delete("api/ccnfSlabWise/deleteCcnfSlabwiseCommissionById/" + latestSlabWiseCommissionId).then()
				.statusCode(200);
	}

}
