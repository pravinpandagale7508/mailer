package com.utcl.ccnfservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

import com.utcl.ccnfservice.master.entity.State;
import com.utcl.ccnfservice.master.repo.StateRepo;
import com.utcl.ccnfservice.master.service.StateService;
import com.utcl.dto.ccnf.StateDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class StateServiceTest {
	
     @Mock
     private StateRepo stateRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
     
    @InjectMocks
    private StateService stateService;
    
    
    private StateDto stateDto=new StateDto();
    private State state=new State();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        state.setId(1L);
        state.setStateCode("ABC");
 
        stateDto.setId(1L);
        stateDto.setStateCode("ABC");
       // stateDto.setCreatedBy("Varsga1");
       // stateDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(stateDto);
    }
     @Test
     @Order(1)
     public void testAddState() throws Exception {
    	when(stateRepo.save(Mockito.any(State.class))).thenReturn(state);
	    log.info("print my values stateDetails: {}", stateService.addStateDetails(stateDto));
		StateDto addDto = stateService.addStateDetails(stateDto);
		log.info("log stateDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetStateById() throws Exception {
        when(stateRepo.findById(state.getId())).thenReturn(java.util.Optional.of(state));
        state = stateService.getStateByStateId(1L);
        assertEquals("ABC", state.getStateCode());
 
       
    }
    
    @Test
    @Order(3)
    void testGetAllStates() throws Exception {
    	List<State> stateList=new ArrayList<State>();
    	stateList.add(state);
        when(stateRepo.findAll()).thenReturn((stateList));
        List<State> agcList=stateService.getStateDetails();
        assertEquals(agcList, stateList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
