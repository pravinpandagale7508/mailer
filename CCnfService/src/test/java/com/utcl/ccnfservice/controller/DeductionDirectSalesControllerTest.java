package com.utcl.ccnfservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;
import com.utcl.ccnfservice.cement.transaction.repo.DeductiononDirectSalesRepo;
import com.utcl.ccnfservice.cement.transaction.service.DeductionCommisionService;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeductionDirectSalesControllerTest {

	@Mock
	DeductiononDirectSalesRepo deductiononDirectSalesRepo;

	@InjectMocks
	DeductionCommisionService deductionCommisionService;
	private DeductiononDirectSalesDto deductiononDirectSalesDto = new DeductiononDirectSalesDto();
	private DeductiononDirectSales deductiononDirectSales = new DeductiononDirectSales();

	static long latestdeductionId = 0L;
	
	Gson gson=new Gson();
	
	@Test
	@Order(1)
	void testAddDeductionDetails(@Autowired MockMvc mvc) throws Exception {

		DeductiononDirectSalesDto deductionDirectSalesDto = DeductiononDirectSalesDto.builder().deductionId(0L)
				.createdBy("DDD").build();

		String addCcnfDeductionPayload = gson.toJson(deductionDirectSalesDto);

		var getRequest = MockMvcRequestBuilders.post("/api/ccnfDeduction/addCcnfDeductionDirectSales")
				.content(addCcnfDeductionPayload).contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("DDD")).andReturn();
		String loid = mockResponse.getResponse().getContentAsString();
		// JsonPath jsonPath= mockResponse.getResponse().
		JsonPath jsonPath = JsonPath.from(loid);
		latestdeductionId = Long.parseLong(jsonPath.get("deductionId").toString());
		log.info("generated loi id" + latestdeductionId);

		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();

	}

	@Test
	void testGetDedctionList(@Autowired MockMvc mvc) throws Exception {
		List<DeductiononDirectSales> deductionList = new ArrayList<>();

		deductiononDirectSales.setDeductionId(latestdeductionId);
		;
		deductiononDirectSales.setCreatedBy("DDD");

		deductiononDirectSalesDto.setDeductionId(latestdeductionId);
		deductiononDirectSalesDto.setCreatedBy("DDD");

		deductionList.add(deductiononDirectSales);
		log.info("deductionList@@@" + deductionList);

		when(deductiononDirectSalesRepo.findAll()).thenReturn((deductionList));
		var getRequest = MockMvcRequestBuilders.get("/api/ccnfDeduction/getCcnfDirectDeductions")
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	void testgetDeductionById(@Autowired MockMvc mvc) throws Exception {

		when(deductiononDirectSalesRepo.findById(deductiononDirectSales.getDeductionId()))
				.thenReturn(java.util.Optional.of(deductiononDirectSales));
		deductiononDirectSales = deductionCommisionService
				.getDirectDeductionsById(deductiononDirectSales.getDeductionId());

		var getRequest = MockMvcRequestBuilders
				.get("/api/ccnfDeduction/getCcnfDirectDeductionsById/" + latestdeductionId)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("DDD")).andDo(print()).andReturn();
		assertThat(mockResponse).isNotNull();
	}

	@Test
	public void testDeleteLoi(@Autowired MockMvc mvc) throws Exception {
		// given - precondition or setup
		long deductionId = latestdeductionId;
		doNothing().when(deductiononDirectSalesRepo).deleteById(deductionId);
		// when - action or the behaviour that we are going test
		deductionCommisionService.deleteDirectDeductionsById(deductionId);
		var getRequest = MockMvcRequestBuilders
				.delete("/api/ccnfLoiTransaction/deleteCCnfLoiDetailsById/" + latestdeductionId)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk());
		// then - verify the output
		verify(deductiononDirectSalesRepo, times(1)).deleteById(deductionId);
	}

}