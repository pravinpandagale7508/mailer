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
import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.ccnfservice.cement.transaction.repo.CCnfLoiRepo;
import com.utcl.ccnfservice.cement.transaction.service.CcnfTransactionService;
import com.utcl.dto.ccnf.CCnfLoiDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CCnfTransactionControllerTest {

	@Mock
	CCnfLoiRepo ccnfLoiRepo;

	@InjectMocks
	CcnfTransactionService ccnfTransactionService;

	static long latestloiId = 0L;
	
	CCnfCementLoi cCnfCementLoi = new CCnfCementLoi();
	Gson gson=new Gson();

	@Test
	@Order(1)
	void testAddCcnfDetails(@Autowired MockMvc mvc) throws Exception {
		List<Long> slocs = new ArrayList<Long>();
		slocs.add(1234L);
		CCnfLoiDto cCnfLoiDto = CCnfLoiDto.builder().loiId(0L).i2Dists(slocs).build();
		
		String addCcnfLoiPayload = gson.toJson(cCnfLoiDto);
		
		var getRequest = MockMvcRequestBuilders.post("/api/ccnfLoiTransaction/addCcnfLoi").content(addCcnfLoiPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.slabRangeTo").value(300)).andReturn();
		String loid = mockResponse.getResponse().getContentAsString();
		// JsonPath jsonPath= mockResponse.getResponse().
		JsonPath jsonPath = JsonPath.from(loid);
		latestloiId = Long.parseLong(jsonPath.get("loiId").toString());
		log.info("generated loi id" + latestloiId);

	}

	@Test
	@Order(2)
	void testGetListofCcnfLoi(@Autowired MockMvc mvc) throws Exception {
		List<CCnfCementLoi> ccnfLoiList = new ArrayList<>();

		cCnfCementLoi.setLoiId(latestloiId);
		//cCnfCementLoi.setAgentId(2L);
		//cCnfCementLoi.setSlocId(1234L);

		ccnfLoiList.add(cCnfCementLoi);
		log.info("ccnfLoiList@@@" + ccnfLoiList);

		when(ccnfLoiRepo.findAll()).thenReturn((ccnfLoiList));
		var getRequest = MockMvcRequestBuilders.get("/api/ccnfLoiTransaction/getCcnfLoiDetails")
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(3)
	void testGetLoiById(@Autowired MockMvc mvc) throws Exception {
		
		
		when(ccnfLoiRepo.findById(cCnfCementLoi.getLoiId())).thenReturn(java.util.Optional.of(cCnfCementLoi));
		cCnfCementLoi = ccnfTransactionService.getCCnfLoiDetailsById(cCnfCementLoi.getLoiId());

		var getRequest = MockMvcRequestBuilders.get("/api/ccnfLoiTransaction/getCcnfLoiDetailsByid/"+latestloiId)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.slocId").value(1234)).andDo(print()).andReturn();
		assertThat(mockResponse).isNotNull();
	}
	
	@Test
	public void testDeleteLoi(@Autowired MockMvc mvc) throws Exception {
		// given - precondition or setup
		long loiId = latestloiId;
		doNothing().when(ccnfLoiRepo).deleteById(loiId);
		// when - action or the behaviour that we are going test
		ccnfTransactionService.deleteCCnfLoiDetailsById(loiId);
		var getRequest = MockMvcRequestBuilders.delete("/api/ccnfLoiTransaction/deleteCCnfLoiDetailsById/"+latestloiId)
				.contentType(MediaType.APPLICATION_JSON);
		 mvc.perform(getRequest).andExpect(status().isOk());
		// then - verify the output
		verify(ccnfLoiRepo, times(1)).deleteById(loiId);
	}

}
