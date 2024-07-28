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
import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.repo.DepotRepo;
import com.utcl.ccnfservice.master.service.DepotService;
import com.utcl.dto.ccnf.DepotDto;
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
public class DepotControllerTest {
    Gson gson = new Gson();

	ObjectMapper om = new ObjectMapper();
    static long newdepoId = 0L;

	@Mock
	private DepotRepo depotRepo;

	@Mock
	private ModelMapper modelMapper = new ModelMapper();


	Depot user = mock(Depot.class);

	@InjectMocks
	private DepotService depotService;

	private DepotDto depotDto = new DepotDto();
	private Depot depot = new Depot();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		depot.setDepotId(0L);
		depot.setDepotName("ABC");
		depot.setCreatedBy("Varsga1");
		depot.setUpdatedBy("Varsga1");

		depotDto.setDepotId(0L);
		depotDto.setDepotName("ABC");
		depotDto.setCreatedBy("Varsga1");
		depotDto.setUpdatedBy("Varsga1");
		when(modelMapper.map(any(), any())).thenReturn(depotDto);
	}

	@Test
	@Order(3)
	void testGetAllAgencies(@Autowired MockMvc mvc) throws Exception {
		List<Depot> depotList = new ArrayList<Depot>();
		depotList.add(depot);
		log.info("depotList@@@" + depotList);
		when(depotRepo.findAll()).thenReturn((depotList));
		var getRequest = MockMvcRequestBuilders.get("/api/depot/getDepotDetails").accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print()).andReturn();
		assertThat(mockResponse.getResponse().getContentAsString()).isNotBlank();
		assertThat(mockResponse).isNotNull();

	}

	@Test
	@Order(2)
	void testGetDepotById(@Autowired MockMvc mvc) throws Exception {
		when(depotRepo.findById(newdepoId)).thenReturn(java.util.Optional.of(depot));
		Depot newDepot = depotService.getDepotByDepotId(newdepoId);
		List<Depot> depotList = new ArrayList<Depot>();
		depotList.add(newDepot);
		log.info("depotList"+depotList);
		var getRequest = MockMvcRequestBuilders.get("/api/depot/getDepotDetailsById/"+newdepoId).accept(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.depotName").value("ABC")).andReturn();
		assertThat(mockResponse).isNotNull();
		

	}

	@Test
	@Order(1)
	void testAddDepot(@Autowired MockMvc mvc) throws Exception {
			DepotDto depotDto= DepotDto.builder()
		      		  .depotId(0L)
		      		  .depotName("ABC")
		      		  .createdBy("Varsga1")
		      		  .updatedBy("Varsga1")
		      		  .build();
		     	   String addDepotPayload=gson.toJson(depotDto);
		var getRequest = MockMvcRequestBuilders.post("/api/depot/addDepot").content(addDepotPayload)
				.contentType(MediaType.APPLICATION_JSON);
		var mockResponse = mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.depotName").value("ABC")).andReturn();
		String agentid = mockResponse.getResponse().getContentAsString();
	    JsonPath jsonPath = JsonPath.from(agentid);
	    newdepoId = Long.parseLong(jsonPath.get("depotId").toString());
		log.info("generated depotId id" + newdepoId);
	}
	
	@Test
	@Order(4)
	void testUpdateDepot(@Autowired MockMvc mvc) throws Exception {
			DepotDto depotDto= DepotDto.builder()
		      		  .depotId(newdepoId)
		      		  .depotName("PQR")
		      		  .createdBy("Varsga1")
		      		  .updatedBy("Varsga1")
		      		  .build();
		     	   String updateDepotPayload=gson.toJson(depotDto);
		var getRequest = MockMvcRequestBuilders.put("/api/depot/updateDepot").content(updateDepotPayload)
				.contentType(MediaType.APPLICATION_JSON);
		 mvc.perform(getRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.depotName").value("PQR")).andReturn();
		
	}


}