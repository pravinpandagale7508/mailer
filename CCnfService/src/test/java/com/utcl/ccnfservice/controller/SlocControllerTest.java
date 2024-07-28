package com.utcl.ccnfservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.utcl.ccnfservice.master.entity.Sloc;
import com.utcl.ccnfservice.master.repo.SlocRepo;
import com.utcl.ccnfservice.master.service.SlocService;
import com.utcl.dto.ccnf.SlocDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SlocControllerTest {
	 Gson gson = new Gson();
	ObjectMapper om = new ObjectMapper();
	@Mock
	private SlocRepo slocRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();


	Sloc user = mock(Sloc.class);

	@InjectMocks
	private SlocService slocService;

	private SlocDto slocDto = new SlocDto();
	private Sloc sloc = new Sloc();
	
	 static long newSlocId = 0L;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		sloc.setSlocId(40L);
		sloc.setSlocName("ABC");
		sloc.setCreatedBy("Varsga1");
		sloc.setUpdatedBy("Varsga1");

		slocDto.setSlocId(40L);
		slocDto.setSlocName("ABC");
		slocDto.setCreatedBy("Varsga1");
		slocDto.setUpdatedBy("Varsga1");
		when(modelMapper.map(any(), any())).thenReturn(slocDto);
	}

	@Test
	@Order(3)
	void testGetAllSlocs(@Autowired MockMvc mvc) throws Exception {
		List<Sloc> slocList = new ArrayList<Sloc>();
		slocList.add(sloc);
		log.info("slocList@@@" + slocList);
		when(slocRepo.findAll()).thenReturn((slocList));
		var getRequest = MockMvcRequestBuilders.get("/api/sloc/getSlocDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetSlocById(@Autowired MockMvc mvc) throws Exception {
		when(slocRepo.findById(newSlocId)).thenReturn(java.util.Optional.of(sloc));
		Sloc newSloc = slocService.getSlocBySlocId(newSlocId);
		List<Sloc> slocList = new ArrayList<Sloc>();
		slocList.add(newSloc);
		log.info("slocList@@@"+slocList);
		var getRequest = MockMvcRequestBuilders.get("/api/sloc/getSlocById/"+newSlocId).accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.slocName").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();
		

	}

	@Test
	@Order(1)
	void testAddSloc(@Autowired MockMvc mvc) throws Exception {
		 SlocDto slocDto= SlocDto.builder()
	    		  .slocId(0L)
	    		  .slocName("ABC")
	    		  .createdBy("Varsga1")
	    		  .updatedBy("Varsga1")
	    		  .build();
	   	  String addSlocPayload=gson.toJson(slocDto);
		
		var getRequest = MockMvcRequestBuilders.post("/api/sloc/addSloc").content(addSlocPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.slocName").value("ABC")).andReturn();
			String slocid = mockResponse.getResponse().getContentAsString();
	    JsonPath jsonPath = JsonPath.from(slocid);
	    newSlocId = Long.parseLong(jsonPath.get("slocId").toString());
		log.info("generated Sloc id" + newSlocId);
	}
	
	@Test
	@Order(4)
	void testUpdateSloc(@Autowired MockMvc mvc) throws Exception {
		 SlocDto slocDto= SlocDto.builder()
	    		  .slocId(newSlocId)
	    		  .slocName("PQR")
	    		  .createdBy("Varsga1")
	    		  .updatedBy("Varsga1")
	    		  .build();
	   	  String updateSlocPayload=gson.toJson(slocDto);
		
		var getRequest = MockMvcRequestBuilders.put("/api/sloc/updateSloc").content(updateSlocPayload)
				.contentType(MediaType.APPLICATION_JSON);
		 mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.slocName").value("PQR")).andReturn();
			
	}


}