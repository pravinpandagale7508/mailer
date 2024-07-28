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
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(value =MethodOrderer.OrderAnnotation.class )
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeductionDirectSalesControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private static Long latestDeuctionId = 0L;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}
	
	Gson gson=new Gson();

	@Test
	@Order(1)
	public void testAddDeductionLoiDetails() {

		DeductiononDirectSalesDto deductiononDirectSalesDto = DeductiononDirectSalesDto.builder().deductionId(0L).maxAmount(300.0).build();

		String addCcnfDeductionPayload = gson.toJson(deductiononDirectSalesDto);
		
		Response resp = given().when().contentType(ContentType.JSON).body(addCcnfDeductionPayload)
				.post("api/ccnfDeduction/addCcnfDeductionDirectSales").then().statusCode(200)
				.contentType(ContentType.JSON).body("maxAmount", equalTo(300.0f)).extract().response();

		latestDeuctionId = resp.jsonPath().getLong("deductionId");
		log.info("latestDeuctionId" + latestDeuctionId);
	}

	@Test
	@Order(2)
	public void testgetCcnfDirectDeductionsById() {
		log.info("latestDeuctionId+++++++++++" + latestDeuctionId);
		given().when().get("api/ccnfDeduction/getCcnfDirectDeductionsById/" + latestDeuctionId).then().statusCode(200)
				.contentType(ContentType.JSON)
				.body("maxAmount", equalTo(300.0f));
	}

	@Test
	@Order(3)
	public void testgetCcnfDirectDeductions() {
		Response resp = given().when().get("api/ccnfDeduction/getCcnfDirectDeductions").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();
		List<Integer> allDeductionDetails = resp.jsonPath().getList("deductionId");
		log.info("Deduction Details" + allDeductionDetails);
		for (int deductionDetails : allDeductionDetails) {
			log.info("Loi Details: " + deductionDetails);
		}
	}
	
	@Test
	@Order(4)
	public void getDeductionByQuantity() {
		log.info("latestDeuctionId+++++++++++" + latestDeuctionId);
		Double directSalesQty = 300.0;
		Double perCentQty=45.0;//Percent value
		long loiId = 10; // Assuming slabAmount and loiId
		given().when().get("api/ccnfDeduction/getCcnfDirectDeductionsByQuantity/"+perCentQty+"/"+loiId+"/"+directSalesQty).then()
				.statusCode(200).contentType(ContentType.JSON).equals(15000.0);
	}
	
	@Test
	@Order(5)
	public void testdeleteCCnfLoiDetailsById() {
		// Assuming vendor with ID 352 exists
		given().when().delete("api/ccnfLoiTransaction/deleteCCnfLoiDetailsById/"+latestDeuctionId)
		.then().statusCode(200);
	}

}
