package com.utcl.ccnfservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.Zone;
import com.utcl.dto.ccnf.ZoneDto;


public class ZoneDtoTest {

    @Test
    void testGetZoneName() {
    	Zone zone = new Zone();
    	zone.setZoneCode("ABC");
        assertEquals("ABC", zone.getZoneCode());
    }
    
    @Test
    void testGetZoneDetails() {
    	ZoneDto zoneDto = new ZoneDto(1L, "ABC", null);
        assertEquals("ABC", zoneDto.getZoneCode());
        
    }
    
  
    @Test
    void testConstructor() {
    	ZoneDto zoneDto = new ZoneDto(1L, "ABC", null);
        assertEquals("ABC", zoneDto.getZoneCode());
    }

}

