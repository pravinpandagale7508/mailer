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
import com.utcl.ccnfservice.master.entity.GeoTaluka;
import com.utcl.ccnfservice.master.repo.GeoTalukaRepo;
import com.utcl.ccnfservice.master.service.GeoTalukaService;
import com.utcl.dto.ccnf.GeoTalukaDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GeoTalukaControllerTest {
	Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
	static long newgeoTalukaId = 0L;

	@Mock
	private GeoTalukaRepo geoTalukaRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	@InjectMocks
	private GeoTalukaService geoTalukaService;

	private GeoTalukaDto geoTalukaDto = new GeoTalukaDto();
	private GeoTaluka geoTaluka = new GeoTaluka();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		geoTaluka.setId(0L);
		geoTaluka.setTalukaName("ABC");

		geoTalukaDto.setId(0L);
		geoTalukaDto.setTalukaName("ABC");
		when(modelMapper.map(any(), any())).thenReturn(geoTalukaDto);
	}

	@Test
	@Order(3)
	void testGetAllGeoTalukas(@Autowired MockMvc mvc) throws Exception {
		List<GeoTaluka> geoTalukaList = new ArrayList<GeoTaluka>();
		geoTalukaList.add(geoTaluka);
		log.info("geoTalukaList@@@" + geoTalukaList);
		when(geoTalukaRepo.findAll()).thenReturn((geoTalukaList));
		var getRequest = MockMvcRequestBuilders.get("/api/geoTaluka/getGeoTalukaDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetGeoTalukasById(@Autowired MockMvc mvc) throws Exception {
		when(geoTalukaRepo.findById(newgeoTalukaId)).thenReturn(java.util.Optional.of(geoTaluka));
		GeoTaluka newGeoTaluka = geoTalukaService.getGeoTalukaById(newgeoTalukaId);
		List<GeoTaluka> geoTalukaList = new ArrayList<GeoTaluka>();
		geoTalukaList.add(newGeoTaluka);
		log.info("geoTalukaList" + geoTalukaList);
		var getRequest = MockMvcRequestBuilders.get("/api/geoTaluka/getGeoTalukaById/" + newgeoTalukaId)
				.accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.talukaName").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(1)
	void testAddGeoTaluka(@Autowired MockMvc mvc) throws Exception {
		GeoTalukaDto geoTalukaPayloadDto = GeoTalukaDto.builder().id(0L).talukaName("ABC").build();
		String addGeoTalukaPayload = gson.toJson(geoTalukaPayloadDto);
		var getRequest = MockMvcRequestBuilders.post("/api/geoTaluka/addGeoTaluka").content(addGeoTalukaPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.talukaName").value("ABC")).andReturn();
		String geoTalukaid = mockResponse.getResponse().getContentAsString();
		JsonPath jsonPath = JsonPath.from(geoTalukaid);
		newgeoTalukaId = Long.parseLong(jsonPath.get("id").toString());
		log.info("generated geoTalukaId id" + newgeoTalukaId);
	}

	@Test
	@Order(4)
	void testUpdateGeoTaluka(@Autowired MockMvc mvc) throws Exception {
		GeoTalukaDto geoTalukaPayloadDto = GeoTalukaDto.builder().id(newgeoTalukaId).talukaName("PQR").build();
		String updateGeoTalukaPayload = gson.toJson(geoTalukaPayloadDto);
		var getRequest = MockMvcRequestBuilders.put("/api/geoTaluka/updateGeoTaluka").content(updateGeoTalukaPayload)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.talukaName").value("PQR")).andReturn();
	}

}