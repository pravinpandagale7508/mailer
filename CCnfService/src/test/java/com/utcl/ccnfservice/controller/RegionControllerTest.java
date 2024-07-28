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
import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.repo.RegionRepo;
import com.utcl.ccnfservice.master.service.RegionService;
import com.utcl.dto.ccnf.RegionDto;
import com.google.gson.Gson;
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
public class RegionControllerTest {
    Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
	@Mock
	private RegionRepo regionRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	Region user = mock(Region.class);

	@InjectMocks
	private RegionService regionService;

	private RegionDto regiondto = new RegionDto();
	private Region region = new Region();
	
		
    static long newRegionId = 0L;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		region.setRegionId(1L);
		region.setRegionName("ABC");
		region.setCreatedBy("Varsga1");
		region.setUpdatedBy("Varsga1");

		regiondto.setRegionId(1L);
		regiondto.setRegionName("ABC");
		regiondto.setCreatedBy("Varsga1");
		regiondto.setUpdatedBy("Varsga1");
		when(modelMapper.map(any(), any())).thenReturn(regiondto);
	}

	@Test
	@Order(3)
	void testGetAllRegions(@Autowired MockMvc mvc) throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		regionList.add(region);
		log.info("regionList@@@" + regionList);
		when(regionRepo.findAll()).thenReturn((regionList));
		var getRequest = MockMvcRequestBuilders.get("/api/region/getRegionDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetRegionById(@Autowired MockMvc mvc) throws Exception {
	    when(regionRepo.findById(newRegionId)).thenReturn(java.util.Optional.of(region));
		Region region = regionService.getRegionByRegionId(newRegionId);
		List<Region> regList = new ArrayList<Region>();
		regList.add(region);
		log.info("agencyList"+regList);
		var getRequest = MockMvcRequestBuilders.get("/api/region/getRegionById/"+newRegionId).accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("Varsga1")).andReturn();
		assertThat(mockResponse).isNotNull();
		

	}

	@Test
	@Order(1)
	void testAddRegion(@Autowired MockMvc mvc) throws Exception {
		 RegionDto regionDetailsDto= RegionDto.builder()
	    		  .regionId(0L)
	    		  .regionName("ABC")
	    		  .createdBy("Varsga1")
	    		  .updatedBy("Varsga1")
	    		  .build();
	   	  String addRegionPayload=gson.toJson(regionDetailsDto);
	   	  
			var getRequest = MockMvcRequestBuilders.post("/api/region/addRegion").content(addRegionPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse= mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.regionName").value("ABC")).andReturn();
		 
			String regid = mockResponse.getResponse().getContentAsString();
		    JsonPath jsonPath = JsonPath.from(regid);
		    newRegionId = Long.parseLong(jsonPath.get("regionId").toString());
			log.info("generated region id" + newRegionId);


	}
	
	@Test
	@Order(4)
	void testUpdateRegion(@Autowired MockMvc mvc) throws Exception {
		 RegionDto regionDetailsDto= RegionDto.builder()
	    		  .regionId(newRegionId)
	    		  .regionName("PQR")
	    		  .createdBy("Varsga1")
	    		  .updatedBy("Varsga1")
	    		  .build();
	   	  String updateRegionPayload=gson.toJson(regionDetailsDto);
	   	  
			var getRequest = MockMvcRequestBuilders.put("/api/region/updateRegion").content(updateRegionPayload)
				.contentType(MediaType.APPLICATION_JSON);
		 mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.regionName").value("PQR")).andReturn();

	}

}