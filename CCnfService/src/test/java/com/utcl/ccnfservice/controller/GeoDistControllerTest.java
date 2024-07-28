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
import com.utcl.ccnfservice.master.entity.GeoDist;
import com.utcl.ccnfservice.master.repo.GeoDistRepo;
import com.utcl.ccnfservice.master.service.GeoDistService;
import com.utcl.dto.ccnf.GeoDistDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GeoDistControllerTest {
	Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
	static long newgeoDistId = 0L;

	@Mock
	private GeoDistRepo geoDistRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	@InjectMocks
	private GeoDistService geoDistService;

	private GeoDistDto geoDistDto = new GeoDistDto();
	private GeoDist geoDist = new GeoDist();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		geoDist.setId(0L);
		geoDist.setDistDisc("ABC");

		geoDistDto.setId(0L);
		geoDistDto.setDistDisc("ABC");
		when(modelMapper.map(any(), any())).thenReturn(geoDistDto);
	}

	@Test
	@Order(3)
	void testGetAllGeoDists(@Autowired MockMvc mvc) throws Exception {
		List<GeoDist> geoDistList = new ArrayList<GeoDist>();
		geoDistList.add(geoDist);
		log.info("geoDistList@@@" + geoDistList);
		when(geoDistRepo.findAll()).thenReturn((geoDistList));
		var getRequest = MockMvcRequestBuilders.get("/api/geoDist/getGeoDistDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetGeoDistsById(@Autowired MockMvc mvc) throws Exception {
		when(geoDistRepo.findById(newgeoDistId)).thenReturn(java.util.Optional.of(geoDist));
		GeoDist newGeoDist = geoDistService.getGeoDistById(newgeoDistId);
		List<GeoDist> geoDistList = new ArrayList<GeoDist>();
		geoDistList.add(newGeoDist);
		log.info("geoDistList" + geoDistList);
		var getRequest = MockMvcRequestBuilders.get("/api/geoDist/getGeoDistById/" + newgeoDistId)
				.accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.distDisc").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(1)
	void testAddGeoDist(@Autowired MockMvc mvc) throws Exception {
		GeoDistDto geoDistPayloadDto = GeoDistDto.builder().id(0L).distDisc("ABC").build();
		String addGeoDistPayload = gson.toJson(geoDistPayloadDto);
		var getRequest = MockMvcRequestBuilders.post("/api/geoDist/addGeoDist").content(addGeoDistPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.distDisc").value("ABC")).andReturn();
		String geoDistid = mockResponse.getResponse().getContentAsString();
		JsonPath jsonPath = JsonPath.from(geoDistid);
		newgeoDistId = Long.parseLong(jsonPath.get("id").toString());
		log.info("generated geoDistId id" + newgeoDistId);
	}

	@Test
	@Order(4)
	void testUpdateGeoDist(@Autowired MockMvc mvc) throws Exception {
		GeoDistDto geoDistPayloadDto = GeoDistDto.builder().id(newgeoDistId).distDisc("PQR").build();
		String updateGeoDistPayload = gson.toJson(geoDistPayloadDto);
		var getRequest = MockMvcRequestBuilders.put("/api/geoDist/updateGeoDist").content(updateGeoDistPayload)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.distDisc").value("PQR")).andReturn();
	}

}