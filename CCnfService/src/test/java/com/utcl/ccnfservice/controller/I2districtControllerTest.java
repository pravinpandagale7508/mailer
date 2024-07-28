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
import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.ccnfservice.master.repo.I2districtRepo;
import com.utcl.ccnfservice.master.service.I2districtService;
import com.utcl.dto.ccnf.I2districtDto;

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
public class I2districtControllerTest {
    Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
	@Mock
	private I2districtRepo i2districtRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();


	I2district user = mock(I2district.class);

	@InjectMocks
	private I2districtService i2districtService;

	private I2districtDto i2districtDto = new I2districtDto();
	private I2district i2district = new I2district();
	
    static long newdistId = 0L;

	

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		i2district.setDistId(0L);
		i2district.setDistName("ABC");
		i2district.setCreatedBy("Varsga1");
		i2district.setUpdatedBy("Varsga1");

		i2districtDto.setDistId(0L);
		i2districtDto.setDistName("ABC");
		i2districtDto.setCreatedBy("Varsga1");
		i2districtDto.setUpdatedBy("Varsga1");
		when(modelMapper.map(any(), any())).thenReturn(i2districtDto);
	}

	@Test
	@Order(3)
	void testGetAllI2districts(@Autowired MockMvc mvc) throws Exception {
		List<I2district> i2districtList = new ArrayList<I2district>();
		i2districtList.add(i2district);
		log.info("i2districtList@@@" + i2districtList);
		when(i2districtRepo.findAll()).thenReturn((i2districtList));
		var getRequest = MockMvcRequestBuilders.get("/api/i2district/getI2districtDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetI2districtById(@Autowired MockMvc mvc) throws Exception {
		when(i2districtRepo.findById(newdistId)).thenReturn(java.util.Optional.of(i2district));
		I2district newI2district = i2districtService.getI2districtByDistId(newdistId);
		List<I2district> i2districtList = new ArrayList<I2district>();
		i2districtList.add(newI2district);
		log.info("i2districtList"+i2districtList);
		var getRequest = MockMvcRequestBuilders.get("/api/i2district/getI2districtById/"+newdistId).accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("Varsga1")).andReturn();
		assertThat(mockResponse).isNotNull();
		

	}

	@Test
	@Order(1)
	void testAddI2district(@Autowired MockMvc mvc) throws Exception {
		  I2districtDto i2districtDto= I2districtDto.builder()
	      		  .distId(0L)
	      		  .distName("ABC")
	      		  .createdBy("Varsga1")
	      		  .updatedBy("Varsga1")
	      		  .build();
	     	   String addDistrictPayload=gson.toJson(i2districtDto);
		  
		  var getRequest = MockMvcRequestBuilders.post("/api/i2district/addI2district").content(addDistrictPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.distName").value("ABC")).andReturn();
		String districtid = mockResponse.getResponse().getContentAsString();
	    JsonPath jsonPath = JsonPath.from(districtid);
	    newdistId = Long.parseLong(jsonPath.get("distId").toString());
		log.info("generated district id" + newdistId);

	}
	
	@Test
	@Order(1)
	void testUpdateI2district(@Autowired MockMvc mvc) throws Exception {
		  I2districtDto i2districtDto= I2districtDto.builder()
	      		  .distId(0L)
	      		  .distName("PQR")
	      		  .createdBy("Varsga1")
	      		  .updatedBy("Varsga1")
	      		  .build();
	     	   String updateDistrictPayload=gson.toJson(i2districtDto);
		  
		  var getRequest = MockMvcRequestBuilders.put("/api/i2district/updateI2district").content(updateDistrictPayload)
				.contentType(MediaType.APPLICATION_JSON);
		 mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.distName").value("PQR")).andReturn();

	}

}