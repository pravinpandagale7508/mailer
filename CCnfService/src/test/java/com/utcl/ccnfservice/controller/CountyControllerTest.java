package com.utcl.ccnfservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
import com.utcl.ccnfservice.master.entity.County;
import com.utcl.ccnfservice.master.repo.CountyRepo;
import com.utcl.ccnfservice.master.service.CountyService;
import com.utcl.dto.ccnf.CountyDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;


	@SpringBootTest
	@AutoConfigureMockMvc
	@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
	@Slf4j
	public class CountyControllerTest {
	    Gson gson = new Gson();

		ObjectMapper om = new ObjectMapper();
	    static long newcountyId = 0L;

		@Mock
		private CountyRepo countyRepo;

		@Mock
		private ModelMapper modelMapper = new ModelMapper();


		@InjectMocks
		private CountyService countyService;

		private CountyDto countyDto = new CountyDto();
		private County county = new County();
		
		@BeforeEach
		public void setUp() {
			MockitoAnnotations.openMocks(this);
			county.setId(0L);
			county.setCountyName("ABC");
			county.setCreatedBy("Varsga1");
			county.setUpdatedBy("Varsga1");

			county.setId(0L);
			county.setCountyName("ABC");
			countyDto.setCreatedBy("Varsga1");
			countyDto.setUpdatedBy("Varsga1");
			when(modelMapper.map(any(), any())).thenReturn(countyDto);
		}

		@Test
		@Order(3)
		void testGetAllCounties(@Autowired MockMvc mvc) throws Exception {
			List<County> countyList = new ArrayList<County>();
			countyList.add(county);
			log.info("countyList@@@" + countyList);
			when(countyRepo.findAll()).thenReturn((countyList));
			var getRequest = MockMvcRequestBuilders.get("/api/county/getCountyDetails").accept(MediaType.APPLICATION_JSON);
			var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
			assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
			assertThat(mockResponse).isNotNull();

		}

		@Test
		@Order(2)
		void testGetCountyById(@Autowired MockMvc mvc) throws Exception {
			when(countyRepo.findById(newcountyId)).thenReturn(java.util.Optional.of(county));
			County newCounty = countyService.getCountyById(newcountyId);
			List<County> countyList = new ArrayList<County>();
			countyList.add(newCounty);
			log.info("countyList"+countyList);
			var getRequest = MockMvcRequestBuilders.get("/api/county/getCountyById/"+newcountyId).accept(MediaType.APPLICATION_JSON);
			var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
					.andExpect(MockMvcResultMatchers.jsonPath("$.countyName").value("ABC")).andReturn();
			assertThat(mockResponse).isNotNull();
			

		}

		@Test
		@Order(1)
		void testAddCounty(@Autowired MockMvc mvc) throws Exception {
				CountyDto countyPayloadDto= CountyDto.builder()
			      		  .id(0L)
			      		  .countyName("ABC")
			      		  .createdBy("Varsga1")
			      		  .updatedBy("Varsga1")
			      		  .build();
			     	   String addCountyPayload=gson.toJson(countyPayloadDto);
			var getRequest = MockMvcRequestBuilders.post("/api/county/addCounty").content(addCountyPayload)
					.contentType(MediaType.APPLICATION_JSON);
			var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.countyName").value("ABC")).andReturn();
			String countyid = mockResponse.getResponse().getContentAsString();
		    JsonPath jsonPath = JsonPath.from(countyid);
		    newcountyId = Long.parseLong(jsonPath.get("id").toString());
			log.info("generated countyId id" + newcountyId);
		}
		
		@Test
		@Order(4)
		void testUpdateCounty(@Autowired MockMvc mvc) throws Exception {
				CountyDto countyPayloadDto= CountyDto.builder()
			      		  .id(newcountyId)
			      		  .countyName("PQR")
			      		  .createdBy("Varsga1")
			      		  .updatedBy("Varsga1")
			      		  .build();
			     	   String updateCountyPayload=gson.toJson(countyPayloadDto);
			var getRequest = MockMvcRequestBuilders.put("/api/county/updateCounty").content(updateCountyPayload)
					.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(getRequest).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.countyName").value("PQR")).andReturn();
		}


	}