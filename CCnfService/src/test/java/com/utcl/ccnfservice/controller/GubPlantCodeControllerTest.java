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
import com.utcl.ccnfservice.master.entity.GubtPlantCode;
import com.utcl.ccnfservice.master.repo.GubPlantCodeRepo;
import com.utcl.ccnfservice.master.service.GubPlantCodeService;
import com.utcl.dto.ccnf.GubtPlantCodeDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GubPlantCodeControllerTest {
    Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
    static long newguoId = 0L;

	@Mock
	private GubPlantCodeRepo gubtPlantCodeRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();


	@InjectMocks
	private GubPlantCodeService gubtPlantCodeService;

	private GubtPlantCodeDto gubtPlantCodeDto = new GubtPlantCodeDto();
	private GubtPlantCode gubtPlantCode = new GubtPlantCode();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		gubtPlantCode.setGubId(0L);
		gubtPlantCode.setGubName("ABC");
		gubtPlantCode.setCreatedBy("Varsga1");
		gubtPlantCode.setUpdatedBy("Varsga1");

		gubtPlantCodeDto.setGubId(0L);
		gubtPlantCodeDto.setGubName("ABC");
		gubtPlantCodeDto.setCreatedBy("Varsga1");
		gubtPlantCodeDto.setUpdatedBy("Varsga1");
		when(modelMapper.map(any(), any())).thenReturn(gubtPlantCodeDto);
	}

	@Test
	@Order(3)
	void testGetAllAgencies(@Autowired MockMvc mvc) throws Exception {
		List<GubtPlantCode> gubtPlantCodeList = new ArrayList<GubtPlantCode>();
		gubtPlantCodeList.add(gubtPlantCode);
		log.info("gubtPlantCodeList@@@" + gubtPlantCodeList);
		when(gubtPlantCodeRepo.findAll()).thenReturn((gubtPlantCodeList));
		var getRequest = MockMvcRequestBuilders.get("/api/gubPlant/getGubPlantDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetGubPlantCodeById(@Autowired MockMvc mvc) throws Exception {
		when(gubtPlantCodeRepo.findById(newguoId)).thenReturn(java.util.Optional.of(gubtPlantCode));
		GubtPlantCode newGubPlantCode = gubtPlantCodeService.getGubPlantCodeByGubId(newguoId);
		List<GubtPlantCode> gubtPlantCodeList = new ArrayList<GubtPlantCode>();
		gubtPlantCodeList.add(newGubPlantCode);
		log.info("gubtPlantCodeList"+gubtPlantCodeList);
		var getRequest = MockMvcRequestBuilders.get("/api/gubPlant/getGubPlantCodeDetailsById/"+newguoId).accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.gubName").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();
		

	}
	

	@Test
	@Order(1)
	void testAddGubPlantCode(@Autowired MockMvc mvc) throws Exception {
			GubtPlantCodeDto gubtPlantCodeDto= GubtPlantCodeDto.builder()
		      		  .gubId(0L)
		      		  .gubName("ABC")
		      		  .createdBy("Varsga1")
		      		  .updatedBy("Varsga1")
		      		  .build();
		     	   String addGubPlantCodePayload=gson.toJson(gubtPlantCodeDto);
		var getRequest = MockMvcRequestBuilders.post("/api/gubPlant/addGubPlantCode").content(addGubPlantCodePayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.gubName").value("ABC")).andReturn();
		String gubPlantid = mockResponse.getResponse().getContentAsString();
	    JsonPath jsonPath = JsonPath.from(gubPlantid);
	    newguoId = Long.parseLong(jsonPath.get("gubId").toString());
		log.info("generated gubtPlantCodeId id" + newguoId);
	}
	
	@Test
	@Order(4)
	void testUpdateGubPlantCode(@Autowired MockMvc mvc) throws Exception {
			GubtPlantCodeDto gubtPlantCodeDto= GubtPlantCodeDto.builder()
		      		  .gubId(newguoId)
		      		  .gubName("PQR")
		      		  .createdBy("Varsga1")
		      		  .updatedBy("Varsga1")
		      		  .build();
		     	   String updateGubPlantCodePayload=gson.toJson(gubtPlantCodeDto);
		var getRequest = MockMvcRequestBuilders.put("/api/gubPlant/updateGubPlantCode").content(updateGubPlantCodePayload)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.gubName").value("PQR")).andReturn();
		
	}
	
	

}