package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.GeoDist;
import com.utcl.dto.ccnf.GeoDistDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GeoDistDtoTest {

    @Test
    void testGetGeoDistName() {
    	GeoDist geoDist = new GeoDist();
    	geoDist.setDistName("ABC");
        assertEquals("ABC", geoDist.getDistName());
    }
    
    @Test
    void testGetGeoDistDetails() {
    	GeoDistDto geoDistDto = new GeoDistDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", geoDistDto.getDistName());
           
    }
    
  
    @Test
    void testConstructor() {
    	GeoDistDto geoDistDto = new GeoDistDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", geoDistDto.getDistName());
         assertEquals(1, geoDistDto.getId());
    }

}

