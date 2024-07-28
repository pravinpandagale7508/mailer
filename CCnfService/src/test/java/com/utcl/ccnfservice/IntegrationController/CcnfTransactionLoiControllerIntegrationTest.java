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

import com.google.gson.Gson;
import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.dto.ccnf.CCnfLoiDto;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;
import com.utcl.dto.ccnf.MinimalCommisionDto;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;
import com.utcl.dto.ccnf.data.AgencyData;
import com.utcl.dto.ccnf.data.AgentAndAggrData;
import com.utcl.dto.ccnf.data.CcnfCommitionData;
import com.utcl.dto.ccnf.data.CcnfDirectSalesData;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(value =MethodOrderer.OrderAnnotation.class )
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CcnfTransactionLoiControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private static long latestLoiId = 0;
	
	List<SlabwiseCommisionDto> slabWiseCommissionList=new ArrayList<>();
	List<DeductiononDirectSalesDto> deductiononDirectSalesList=new ArrayList<>();
	List<MinimalCommisionDto> minimalCommisionList=new ArrayList<>();

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
		
		MinimalCommisionDto minimalCommisionDto=MinimalCommisionDto.builder().minimalId(0L).commissiononMT(5000.0).commissionRsOnMT(20.0)
				.slabAmountFrom(2500.0).loiId(latestLoiId).build();
		minimalCommisionList.add(minimalCommisionDto);
		minimalCommisionDto=MinimalCommisionDto.builder().minimalId(0L).commissiononMT(7500.0).commissionRsOnMT(10.0)
				.slabAmountFrom(2500.01).slabAmountTo(5000.00).build();
		minimalCommisionList.add(minimalCommisionDto);
		
		SlabwiseCommisionDto slabWiseComissionDto = SlabwiseCommisionDto.builder().
				slabId(0L).commision(20.00).slabAmountFrom(0.0).slabAmountTo(1000.0).build();
		slabWiseCommissionList.add(slabWiseComissionDto);
		slabWiseComissionDto = SlabwiseCommisionDto.builder().
				slabId(0L).commision(17.00).slabAmountFrom(1000.01).slabAmountTo(2000.0).build();
		slabWiseCommissionList.add(slabWiseComissionDto);
		slabWiseComissionDto = SlabwiseCommisionDto.builder().
				slabId(0L).commision(15.00).slabAmountFrom(2000.01).slabAmountTo(3000.0).build();
		slabWiseCommissionList.add(slabWiseComissionDto);
		slabWiseComissionDto = SlabwiseCommisionDto.builder().
				slabId(0L).commision(15.00).slabAmountFrom(3000.01).build();
		slabWiseCommissionList.add(slabWiseComissionDto);
		
		DeductiononDirectSalesDto deductiononDirectSalesDto=DeductiononDirectSalesDto.builder().deductionId(0L).minAmount(0.0).maxAmount(75.0)
				.commision(2.0).slabAmountFrom(0.0).slabAmountTo(25.01).build();
		deductiononDirectSalesList.add(deductiononDirectSalesDto);
		deductiononDirectSalesDto=DeductiononDirectSalesDto.builder().deductionId(0L).minAmount(0.0).maxAmount(75.0)
				.commision(1.0).slabAmountFrom(25.01).slabAmountTo(50.00).build();
		deductiononDirectSalesList.add(deductiononDirectSalesDto);
		deductiononDirectSalesDto=DeductiononDirectSalesDto.builder().deductionId(0L).minAmount(0.0).maxAmount(75.0)
				.commision(0.75).slabAmountFrom(50.01).slabAmountTo(75.00).build();
		deductiononDirectSalesList.add(deductiononDirectSalesDto);
		deductiononDirectSalesDto=DeductiononDirectSalesDto.builder().deductionId(0L).minAmount(0.0).maxAmount(75.0)
				.commision(0.50).slabAmountFrom(75.01).build();
		deductiononDirectSalesList.add(deductiononDirectSalesDto);
	}
	
	Gson gson=new Gson();

	@Test
	@Order(1)
	public void testaddCcnfLoiDetails() {
		List<Long> regionList= new ArrayList<>();
		regionList.add(1L);
		regionList.add(2L);
		regionList.add(3L);
		AgencyData agencyData=AgencyData.builder().agentId(1L).regionIds(regionList).build();
		AgentAndAggrData agentAndAggrData=AgentAndAggrData.builder().billingCycleType("Monthly").build();
		CcnfCommitionData ccnfCommitionData=CcnfCommitionData.builder().minCommitionRange(0.0).maxCommitionRange(3000.0).slabWiseCommisions(slabWiseCommissionList)
				.isMetroCommition(false).isMinimumCommition(false).isGubtHandledByCCnf(false).isInterunitSale(false).isInterCompanySale(false).isOtherDepoCommission(false).build();
		CcnfDirectSalesData ccnfDirectSalesData=CcnfDirectSalesData.builder().directSalesDtos(deductiononDirectSalesList).isDeduOnDirSales(true).isReducedCommByComp(false).build();
		CCnfLoiRequest cCnfLoiRequest=CCnfLoiRequest.builder().loiId(latestLoiId).agencyData(agencyData).agentAndAggrData(agentAndAggrData)
				.ccnfCommitionData(ccnfCommitionData).ccnfDirectSalesData(ccnfDirectSalesData).ccnfGodownKeeperData(null).build();
				
//		List<Long> slocs = new ArrayList<Long>();
//		slocs.add(1234L);
//		CCnfLoiDto cCnfLoiDto = CCnfLoiDto.builder().loiId(0L).i2Dists(slocs).build();
//
//		String addCcnfLoiPayload = gson.toJson(cCnfLoiDto);
//		
//		Response resp = given().when().contentType(ContentType.JSON).body(addCcnfLoiPayload)
//				.post("api/ccnfLoiTransaction/addCcnfLoi").then().statusCode(200)
//				.contentType(ContentType.JSON).body("slocIds", equalTo(slocs)).extract().response();

		String addCcnfLoiPayload = gson.toJson(cCnfLoiRequest);
		
		Response resp = given().when().contentType(ContentType.JSON).body(addCcnfLoiPayload)
				.post("api/ccnfLoiTransaction/addCcnfLoi").then().statusCode(200)
				.contentType(ContentType.JSON).body("billingCycleType", equalTo("Monthly")).extract().response();
		log.info("response payload"+resp.asPrettyString());
		latestLoiId = Integer.parseInt(resp.jsonPath().get("loiId").toString());
		log.info("latestLoiId" + latestLoiId);
	}
	
	@Test
	@Order(2)
	public void testgetCcnfLoiDetailsById() {
		log.info("loiId+++++++++++++++"+latestLoiId);
		given().when().get("api/ccnfLoiTransaction/getCcnfLoiDetailsByid/"+latestLoiId).then().statusCode(200)
				.contentType(ContentType.JSON)
				.body("billingCycleType", equalTo("Monthly"));
	}

	@Test
	@Order(3)
	public void testgetCcnfLoiDetails()  {
		Response resp = given().when().get("api/ccnfLoiTransaction/getCcnfLoiDetails").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();

		List<Integer>allLoiDetails=	resp.jsonPath().getList("loiId");
		 log.info("allLoiDetails"+allLoiDetails);
		for(int loiDetails : allLoiDetails)
		{
			log.info("Loi Details: " + loiDetails);
		}
	}

	@Test
	@Order(4)
	public void testdeleteCcnfLoiDetails() {
		// Assuming vendor with ID 352 exists
		given().when().delete("api/ccnfLoiTransaction/deleteCCnfLoiDetailsById/"+latestLoiId).then().statusCode(200);
	}
}
