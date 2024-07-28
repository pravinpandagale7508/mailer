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
import com.utcl.ccnfservice.master.entity.Zone;
import com.utcl.ccnfservice.master.repo.ZoneRepo;
import com.utcl.ccnfservice.master.service.ZoneService;
import com.utcl.dto.ccnf.ZoneDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ZoneControllerTest4 {
	Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
	static long newzoneId = 0L;

	@Mock
	private ZoneRepo zoneRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	@InjectMocks
	private ZoneService zoneService;

	private ZoneDto zoneDto = new ZoneDto();
	private Zone zone = new Zone();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		zone.setId(0L);
		zone.setZoneCode("ABC");

		zoneDto.setId(0L);
		zoneDto.setZoneCode("ABC");
		when(modelMapper.map(any(), any())).thenReturn(zoneDto);
	}

	@Test
	@Order(3)
	void testGetAllZones(@Autowired MockMvc mvc) throws Exception {
		List<Zone> zoneList = new ArrayList<Zone>();
		zoneList.add(zone);
		log.info("zoneList@@@" + zoneList);
		when(zoneRepo.findAll()).thenReturn((zoneList));
		var getRequest = MockMvcRequestBuilders.get("/api/zone/getZoneDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetZonesById(@Autowired MockMvc mvc) throws Exception {
		when(zoneRepo.findById(newzoneId)).thenReturn(java.util.Optional.of(zone));
		Zone newZone = zoneService.getZoneByZoneId(newzoneId);
		List<Zone> zoneList = new ArrayList<Zone>();
		zoneList.add(newZone);
		log.info("zoneList" + zoneList);
		var getRequest = MockMvcRequestBuilders.get("/api/zone/getZoneById/" + newzoneId)
				.accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.zoneCode").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(1)
	void testAddZone(@Autowired MockMvc mvc) throws Exception {
		ZoneDto zonePayloadDto = ZoneDto.builder().id(0L).zoneCode("ABC").build();
		String addZonePayload = gson.toJson(zonePayloadDto);
		var getRequest = MockMvcRequestBuilders.post("/api/zone/addZone").content(addZonePayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.zoneCode").value("ABC")).andReturn();
		String zoneid = mockResponse.getResponse().getContentAsString();
		JsonPath jsonPath = JsonPath.from(zoneid);
		newzoneId = Long.parseLong(jsonPath.get("id").toString());
		log.info("generated zoneId id" + newzoneId);
	}

	@Test
	@Order(4)
	void testUpdateZone(@Autowired MockMvc mvc) throws Exception {
		ZoneDto zonePayloadDto = ZoneDto.builder().id(newzoneId).zoneCode("PQR").build();
		String updateZonePayload = gson.toJson(zonePayloadDto);
		var getRequest = MockMvcRequestBuilders.put("/api/zone/updateZone").content(updateZonePayload)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.zoneCode").value("PQR")).andReturn();
	}

}