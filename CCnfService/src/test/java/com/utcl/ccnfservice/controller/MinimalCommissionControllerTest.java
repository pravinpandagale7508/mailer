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
import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;
import com.utcl.ccnfservice.cement.transaction.repo.MinimalCommisionRepo;
import com.utcl.ccnfservice.cement.transaction.service.MinimalCommisionService;
import com.utcl.dto.ccnf.MinimalCommisionDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MinimalCommissionControllerTest {

	@Mock
	MinimalCommisionRepo minimalCommisionRepo;

	@InjectMocks
	MinimalCommisionService minimalCommissionService;
	private MinimalCommisionDto minimalCommissionDto = new MinimalCommisionDto();
	private MinimalCommision minimalCommission = new MinimalCommision();


	static long latestminimalId = 0L;

	Gson gson=new Gson();
	
	@Test
	@Order(1)
	void testAddMinimalDetails(@Autowired MockMvc mvc) throws Exception {
		MinimalCommisionDto minimalCommisionDto = MinimalCommisionDto.builder().minimalId(latestminimalId).createdBy("SSS").build();

		String addMinimalCommissionPayload = gson.toJson(minimalCommisionDto);
		var getRequest = MockMvcRequestBuilders.post("/api/ccnfMinimalCommission/addCcnfMinimalCommission")
				.content(addMinimalCommissionPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("SSS")).andReturn();
		String loid = mockResponse.getResponse().getContentAsString();
		// JsonPath jsonPath= mockResponse.getResponse().
		JsonPath jsonPath = JsonPath.from(loid);
		latestminimalId = Long.parseLong(jsonPath.get("minimalId").toString());
		log.info("generated loi id" + latestminimalId);

		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();

	}

	@Test
	@Order(2)
	void testGetMinimalCommissionList(@Autowired MockMvc mvc) throws Exception {
		List<MinimalCommision> deductionList = new ArrayList<>();

		minimalCommission.setMinimalId(latestminimalId);;
		minimalCommission.setCreatedBy("MMM");

		minimalCommissionDto.setMinimalId(latestminimalId);
		minimalCommissionDto.setCreatedBy("MMM");

		deductionList.add(minimalCommission);
		log.info("deductionList@@@" + deductionList);

		when(minimalCommisionRepo.findAll()).thenReturn((deductionList));
		var getRequest = MockMvcRequestBuilders.get("/api/ccnfMinimalCommission/getCcnfMinimalCommission")
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(3)
	void testgetMinimalCommissionById(@Autowired MockMvc mvc) throws Exception {
	
		when(minimalCommisionRepo.findById(minimalCommission.getMinimalId())).thenReturn(java.util.Optional.of(minimalCommission));
		minimalCommission = minimalCommissionService.getMinimalCommisionCommissionById(minimalCommission.getMinimalId());

		var getRequest = MockMvcRequestBuilders.get("/api/ccnfMinimalCommission/getCcnfMinimalCommissionById/" + latestminimalId)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("SSS")).andReturn();
		assertThat(mockResponse).isNotNull();
	}
	
	@Test
	@Order(4)
	public void testDeleteMinimalCommissionByID(@Autowired MockMvc mvc) throws Exception {
		// given - precondition or setup
		long minimalId = latestminimalId;
		doNothing().when(minimalCommisionRepo).deleteById(minimalId);
		// when - action or the behaviour that we are going test
		minimalCommissionService.deleteMinimalCommisionCommissionById(minimalId);
		var getRequest = MockMvcRequestBuilders.delete("/api/ccnfMinimalCommission/deleteCcnfMinimalCommisionById/"+latestminimalId)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk());
		// then - verify the output
		verify(minimalCommisionRepo, times(1)).deleteById(minimalId);
	}

}
