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
import com.utcl.ccnfservice.master.entity.State;
import com.utcl.ccnfservice.master.repo.StateRepo;
import com.utcl.ccnfservice.master.service.StateService;
import com.utcl.dto.ccnf.StateDto;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class StateControllerTest {
	Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
	static long newstateId = 0L;

	@Mock
	private StateRepo stateRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	@InjectMocks
	private StateService stateService;

	private StateDto stateDto = new StateDto();
	private State state = new State();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		state.setId(0L);
		state.setStateCode("ABC");

		stateDto.setId(0L);
		stateDto.setStateCode("ABC");
		when(modelMapper.map(any(), any())).thenReturn(stateDto);
	}

	@Test
	@Order(3)
	void testGetAllStates(@Autowired MockMvc mvc) throws Exception {
		List<State> stateList = new ArrayList<State>();
		stateList.add(state);
		log.info("stateList@@@" + stateList);
		when(stateRepo.findAll()).thenReturn((stateList));
		var getRequest = MockMvcRequestBuilders.get("/api/state/getStateDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetStatesById(@Autowired MockMvc mvc) throws Exception {
		when(stateRepo.findById(newstateId)).thenReturn(java.util.Optional.of(state));
		State newState = stateService.getStateByStateId(newstateId);
		List<State> stateList = new ArrayList<State>();
		stateList.add(newState);
		log.info("stateList" + stateList);
		var getRequest = MockMvcRequestBuilders.get("/api/state/getStateById/" + newstateId)
				.accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.stateCode").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(1)
	void testAddState(@Autowired MockMvc mvc) throws Exception {
		StateDto statePayloadDto = StateDto.builder().id(0L).stateCode("ABC").build();
		String addStatePayload = gson.toJson(statePayloadDto);
		var getRequest = MockMvcRequestBuilders.post("/api/state/addState").content(addStatePayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.stateCode").value("ABC")).andReturn();
		String stateid = mockResponse.getResponse().getContentAsString();
		JsonPath jsonPath = JsonPath.from(stateid);
		newstateId = Long.parseLong(jsonPath.get("id").toString());
		log.info("generated stateId id" + newstateId);
	}

	@Test
	@Order(4)
	void testUpdateState(@Autowired MockMvc mvc) throws Exception {
		StateDto statePayloadDto = StateDto.builder().id(newstateId).stateCode("PQR").build();
		String updateStatePayload = gson.toJson(statePayloadDto);
		var getRequest = MockMvcRequestBuilders.put("/api/state/updateState").content(updateStatePayload)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.stateCode").value("PQR")).andReturn();
	}

}