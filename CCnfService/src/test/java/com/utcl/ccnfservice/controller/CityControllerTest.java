package com.utcl.ccnfservice.controller;

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
import com.utcl.ccnfservice.master.entity.City;
import com.utcl.ccnfservice.master.repo.CityRepo;
import com.utcl.ccnfservice.master.service.CityService;
import com.utcl.dto.ccnf.CityDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;


	@SpringBootTest
	@AutoConfigureMockMvc
	@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
	@Slf4j
	public class CityControllerTest {
	    Gson gson = new Gson();

		ObjectMapper om = new ObjectMapper();
	    static long newcityId = 0L;

		@Mock
		private CityRepo cityRepo;

		@Mock
		private ModelMapper modelMapper = new ModelMapper();

		City user = mock(City.class);

		@InjectMocks
		private CityService cityService;

		private CityDto cityDto = new CityDto();
		private City city = new City();
		
		@BeforeEach
		public void setUp() {
			MockitoAnnotations.openMocks(this);
			city.setId(0L);
			city.setI2TqId(0L);
			city.setCityName("ABC");
			city.setCreatedBy("Varsga1");
			city.setUpdatedBy("Varsga1");

			city.setId(0L);
			city.setI2TqId(0L);
			city.setCityName("ABC");
			cityDto.setCreatedBy("Varsga1");
			cityDto.setUpdatedBy("Varsga1");
			when(modelMapper.map(any(), any())).thenReturn(cityDto);
		}

		@Test
		@Order(3)
		void testGetAllCities(@Autowired MockMvc mvc) throws Exception {
			List<City> cityList = new ArrayList<City>();
			cityList.add(city);
			log.info("cityList@@@" + cityList);
			when(cityRepo.findAll()).thenReturn((cityList));
			var getRequest = MockMvcRequestBuilders.get("/api/city/getCityDetails").accept(MediaType.APPLICATION_JSON);
			var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
			assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
			assertThat(mockResponse).isNotNull();

		}

		@Test
		@Order(2)
		void testGetCityById(@Autowired MockMvc mvc) throws Exception {
			when(cityRepo.findById(newcityId)).thenReturn(java.util.Optional.of(city));
			City newCity = cityService.getCityById(newcityId);
			List<City> cityList = new ArrayList<City>();
			cityList.add(newCity);
			log.info("cityList"+cityList);
			var getRequest = MockMvcRequestBuilders.get("/api/city/getCityById/"+newcityId).accept(MediaType.APPLICATION_JSON);
			var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
					.andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value("ABC")).andReturn();
			assertThat(mockResponse).isNotNull();
			

		}

		@Test
		@Order(1)
		void testAddCity(@Autowired MockMvc mvc) throws Exception {
				CityDto cityPayloadDto= CityDto.builder()
			      		  .id(0L)
			      		  .cityName("ABC")
			      		  .createdBy("Varsga1")
			      		  .updatedBy("Varsga1")
			      		  .build();
			     	   String addCityPayload=gson.toJson(cityPayloadDto);
			var getRequest = MockMvcRequestBuilders.post("/api/city/addCity").content(addCityPayload)
					.contentType(MediaType.APPLICATION_JSON);
			var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value("ABC")).andReturn();
			String cityid = mockResponse.getResponse().getContentAsString();
		    JsonPath jsonPath = JsonPath.from(cityid);
		    newcityId = Long.parseLong(jsonPath.get("id").toString());
			log.info("generated cityId id" + newcityId);
		}
		
		@Test
		@Order(4)
		void testUpdateCity(@Autowired MockMvc mvc) throws Exception {
				CityDto cityPayloadDto= CityDto.builder()
			      		  .id(newcityId)
			      		  .cityName("PQR")
			      		  .createdBy("Varsga1")
			      		  .updatedBy("Varsga1")
			      		  .build();
			     	   String updateCityPayload=gson.toJson(cityPayloadDto);
			var getRequest = MockMvcRequestBuilders.put("/api/city/updateCity").content(updateCityPayload)
					.contentType(MediaType.APPLICATION_JSON);
			mvc.perform(getRequest).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value("PQR")).andReturn();
		}


	}