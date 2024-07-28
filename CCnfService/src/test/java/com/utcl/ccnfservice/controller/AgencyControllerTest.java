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
import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.repo.AgencyRepo;
import com.utcl.ccnfservice.master.service.AgencyService;
import com.utcl.dto.ccnf.AgencyDto;
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
public class AgencyControllerTest {
    Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
    static long newagentId = 0L;

	@Mock
	private AgencyRepo agencyRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	Agency user = mock(Agency.class);

	@InjectMocks
	private AgencyService agencyService;

	private AgencyDto agencyDto = new AgencyDto();
	private Agency agency = new Agency();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		agency.setAgentId(0L);
		agency.setAgentName("ABC");
		agency.setCreatedBy("Varsga1");
		agency.setUpdatedBy("Varsga1");

		agencyDto.setAgentId(0L);
		agencyDto.setAgentName("ABC");
			when(modelMapper.map(any(), any())).thenReturn(agencyDto);
	}

	@Test
	@Order(3)
	void testGetAllAgencies(@Autowired MockMvc mvc) throws Exception {
		List<Agency> agencyList = new ArrayList<Agency>();
		agencyList.add(agency);
		log.info("agencyList@@@" + agencyList);
		when(agencyRepo.findAll()).thenReturn((agencyList));
		var getRequest = MockMvcRequestBuilders.get("/api/agent/getAgencyDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetAgencyById(@Autowired MockMvc mvc) throws Exception {
		when(agencyRepo.findById(newagentId)).thenReturn(java.util.Optional.of(agency));
		Agency newAgency = agencyService.getAgencyByAgentId(newagentId);
		List<Agency> agencyList = new ArrayList<Agency>();
		agencyList.add(newAgency);
		log.info("agencyList"+agencyList);
		var getRequest = MockMvcRequestBuilders.get("/api/agent/getAgentDetailsByAgentId/"+newagentId).accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.agentName").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();
		

	}

	@Test
	@Order(1)
	void testAddAgency(@Autowired MockMvc mvc) throws Exception {
			AgencyDto agencyPayloadDto= AgencyDto.builder()
		      		  .agentId(0L)
		      		  .agentName("ABC")
		      		  .build();
		     	   String addAgencyPayload=gson.toJson(agencyPayloadDto);
		var getRequest = MockMvcRequestBuilders.post("/api/agent/addAgency").content(addAgencyPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.agentName").value("ABC")).andReturn();
		String agentid = mockResponse.getResponse().getContentAsString();
	    JsonPath jsonPath = JsonPath.from(agentid);
	    newagentId = Long.parseLong(jsonPath.get("agentId").toString());
		log.info("generated agencyId id" + newagentId);
	}
	
	@Test
	@Order(4)
	void testUpdateAgency(@Autowired MockMvc mvc) throws Exception {
			AgencyDto agencyPayloadDto= AgencyDto.builder()
		      		  .agentId(newagentId)
		      		  .agentName("PQR")
		       		  .build();
		     	   String updateAgencyPayload=gson.toJson(agencyPayloadDto);
		var getRequest = MockMvcRequestBuilders.put("/api/agent/updateAgency").content(updateAgencyPayload)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.agentName").value("PQR")).andReturn();
	}


}