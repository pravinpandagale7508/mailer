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
import com.utcl.ccnfservice.master.entity.I2Taluka;
import com.utcl.ccnfservice.master.repo.I2TalukaRepo;
import com.utcl.ccnfservice.master.service.I2TalukaService;
import com.utcl.dto.ccnf.I2TalukaDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class I2TalukaControllerTest {
	Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
	static long newi2TalukaId = 0L;

	@Mock
	private I2TalukaRepo i2TalukaRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	@InjectMocks
	private I2TalukaService i2TalukaService;

	private I2TalukaDto i2TalukaDto = new I2TalukaDto();
	private I2Taluka i2Taluka = new I2Taluka();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		i2Taluka.setId(0L);
		i2Taluka.setTalukaName("ABC");

		i2TalukaDto.setId(0L);
		i2TalukaDto.setTalukaName("ABC");
		when(modelMapper.map(any(), any())).thenReturn(i2TalukaDto);
	}

	@Test
	@Order(3)
	void testGetAllI2Talukas(@Autowired MockMvc mvc) throws Exception {
		List<I2Taluka> i2TalukaList = new ArrayList<I2Taluka>();
		i2TalukaList.add(i2Taluka);
		log.info("i2TalukaList@@@" + i2TalukaList);
		when(i2TalukaRepo.findAll()).thenReturn((i2TalukaList));
		var getRequest = MockMvcRequestBuilders.get("/api/i2Taluka/getI2TalukaDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetI2TalukasById(@Autowired MockMvc mvc) throws Exception {
		when(i2TalukaRepo.findById(newi2TalukaId)).thenReturn(java.util.Optional.of(i2Taluka));
		I2Taluka newI2Taluka = i2TalukaService.getI2TalukaByDistId(newi2TalukaId);
		List<I2Taluka> i2TalukaList = new ArrayList<I2Taluka>();
		i2TalukaList.add(newI2Taluka);
		log.info("i2TalukaList" + i2TalukaList);
		var getRequest = MockMvcRequestBuilders.get("/api/i2Taluka/getI2TalukaById/" + newi2TalukaId)
				.accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.talukaName").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(1)
	void testAddI2Taluka(@Autowired MockMvc mvc) throws Exception {
		I2TalukaDto i2TalukaPayloadDto = I2TalukaDto.builder().id(0L).talukaName("ABC").build();
		String addI2TalukaPayload = gson.toJson(i2TalukaPayloadDto);
		var getRequest = MockMvcRequestBuilders.post("/api/i2Taluka/addI2Taluka").content(addI2TalukaPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.talukaName").value("ABC")).andReturn();
		String i2Talukaid = mockResponse.getResponse().getContentAsString();
		JsonPath jsonPath = JsonPath.from(i2Talukaid);
		newi2TalukaId = Long.parseLong(jsonPath.get("id").toString());
		log.info("generated i2TalukaId id" + newi2TalukaId);
	}

	@Test
	@Order(4)
	void testUpdateI2Taluka(@Autowired MockMvc mvc) throws Exception {
		I2TalukaDto i2TalukaPayloadDto = I2TalukaDto.builder().id(newi2TalukaId).talukaName("PQR").build();
		String updateI2TalukaPayload = gson.toJson(i2TalukaPayloadDto);
		var getRequest = MockMvcRequestBuilders.put("/api/i2Taluka/updateI2Taluka").content(updateI2TalukaPayload)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.talukaName").value("PQR")).andReturn();
	}

}