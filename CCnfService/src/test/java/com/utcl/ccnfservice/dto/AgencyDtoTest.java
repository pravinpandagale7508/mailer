package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.dto.ccnf.AgencyDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AgencyDtoTest {

    @Test
    void testAgencyName() {
    	Agency agency = new Agency();
    	agency.setAgentName("ABC");
        assertEquals("ABC", agency.getAgentName());
    }
    
    @Test
    void testGetAgencyDetails() {
    	AgencyDto agencyDto = new AgencyDto(1L, "ABC", null, null, null, null);
        assertEquals("ABC", agencyDto.getAgentName());
         
    }
    
  
    @Test
    void testConstructor() {
    	AgencyDto agencyDto = new AgencyDto(1L, "ABC", null, null, null, null);
         assertEquals("ABC", agencyDto.getAgentName());
         assertEquals(1, agencyDto.getAgentId());
    }

}

