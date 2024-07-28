package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.State;
import com.utcl.dto.ccnf.StateDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StateDtoTest {

    @Test
    void testGetStateName() {
    	State state = new State();
    	state.setStateCode("ABC");
        assertEquals("ABC", state.getStateCode());
    }
    
    @Test
    void testGetStateDetails() {
    	StateDto stateDto = new StateDto(1L,null, "ABC",null);
        assertEquals("ABC", stateDto.getStateCode());
       
        
    }
    
  
    @Test
    void testConstructor() {
    	StateDto stateDto = new StateDto(1L,null, "ABC",null);
        assertEquals("ABC", stateDto.getStateCode());
         assertEquals(1, stateDto.getId());
    }

}

