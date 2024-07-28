package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.dto.ccnf.DepotDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepotDtoTest {

    @Test
    void testGetDepotName() {
    	Depot depot = new Depot();
    	depot.setDepotName("ABC");
        assertEquals("ABC", depot.getDepotName());
    }
    
    @Test
    void testGetDepotDetails() {
    	DepotDto depotDto = new DepotDto(1L, "ABC", null, null, null, "Varsha", "Varsha");
        assertEquals("ABC", depotDto.getDepotName());
        assertEquals("Varsha", depotDto.getCreatedBy());
        assertEquals("Varsha", depotDto.getUpdatedBy());
        
    }
    
  
    @Test
    void testConstructor() {
    	DepotDto depotDto = new DepotDto(1L, "ABC", null, null, null, "Varsha", "Varsha");
         assertEquals("ABC", depotDto.getDepotName());
         assertEquals(1, depotDto.getDepotId());
    }

}

