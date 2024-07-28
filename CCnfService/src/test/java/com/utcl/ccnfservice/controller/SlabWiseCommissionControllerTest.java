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
import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;
import com.utcl.ccnfservice.cement.transaction.repo.SlabwisecommisionRepo;
import com.utcl.ccnfservice.cement.transaction.service.CcnfSlabwiseCommisionService;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SlabWiseCommissionControllerTest {

	@Mock
	SlabwisecommisionRepo slabwisecommisionRepo;

	@InjectMocks
	CcnfSlabwiseCommisionService slabwiseCommisionService;
	private SlabwiseCommisionDto slabwiseCommisionDto = new SlabwiseCommisionDto();
	private SlabwiseCommision slabwiseCommision = new SlabwiseCommision();


	static long latesSlabId = 0L;
	Gson gson=new Gson();
	
	@Test
	@Order(1)
	void testAddSlabDetails(@Autowired MockMvc mvc) throws Exception {

		SlabwiseCommisionDto slabWiseComissionDto = SlabwiseCommisionDto.builder().slabId(latesSlabId).minAmount(30.1).createdBy("SSS").build();

		String slabwisePayload = gson.toJson(slabWiseComissionDto);
		
		var getRequest = MockMvcRequestBuilders.post("/api/ccnfSlabWise/addCcnfSlabwiseCommission")
				.content(slabwisePayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("SSS"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.minAmount").value(30.1f))
				.andReturn();
		String slabId = mockResponse.getResponse().getContentAsString();
		// JsonPath jsonPath= mockResponse.getResponse().
		JsonPath jsonPath = JsonPath.from(slabId);
		latesSlabId = Long.parseLong(jsonPath.get("slabId").toString());
		log.info("generated latesSlabId id" + latesSlabId);

		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();

	}

	@Test
	void testGetSlabWiseCommissionList(@Autowired MockMvc mvc) throws Exception {
		List<SlabwiseCommision> slabList = new ArrayList<>();

		slabwiseCommision.setSlabId(latesSlabId);;
		slabwiseCommision.setCreatedBy("SSS");

		slabwiseCommisionDto.setSlabId(latesSlabId);
		slabwiseCommisionDto.setCreatedBy("SSS");

		slabList.add(slabwiseCommision);
		log.info("slabList@@@" + slabList);

		when(slabwisecommisionRepo.findAll()).thenReturn((slabList));
		var getRequest = MockMvcRequestBuilders.get("/api/ccnfSlabWise/getCcnfSlabwiseCommissions")
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testgetSlabWiseCommissionById(@Autowired MockMvc mvc) throws Exception {
		
		slabwiseCommision.setSlabId(latesSlabId);;
		slabwiseCommision.setCreatedBy("SSS");
		
		log.info("latesSlabId"+latesSlabId);
		when(slabwisecommisionRepo.findById(slabwiseCommision.getSlabId())).thenReturn(java.util.Optional.of(slabwiseCommision));
		slabwiseCommision = slabwiseCommisionService.getSlabwiseCommissionById(slabwiseCommision.getSlabId());
		
		var getRequest = MockMvcRequestBuilders.get("/api/ccnfSlabWise/getCcnfSlabwiseCommissionsByid/"+latesSlabId)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
				//.andExpect(MockMvcResultMatchers.jsonPath("$.loiId").value(1)).andReturn();
		assertThat(mockResponse).isNotNull();
	}
	
	@Test
	@Order(4)
	public void testDeleteSlab(@Autowired MockMvc mvc) throws Exception {
		// given - precondition or setup
		long slabId = latesSlabId;
		doNothing().when(slabwisecommisionRepo).deleteById(slabId);
		// when - action or the behaviour that we are going test
		slabwiseCommisionService.deleteSlabwiseCommissionById(slabId);
		var getRequest = MockMvcRequestBuilders.delete("/api/ccnfSlabWise/deleteCcnfSlabwiseCommissionById/"+latesSlabId)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk());
		// then - verify the output
		verify(slabwisecommisionRepo, times(1)).deleteById(slabId);
	}

}
