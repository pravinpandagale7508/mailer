package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.City;
import com.utcl.dto.ccnf.CityDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CityDtoTest {

    @Test
    void testGetCityName() {
    	City sloc = new City();
    	sloc.setCityName("ABC");
        assertEquals("ABC", sloc.getCityName());
    }
    
    @Test
    void testGetCityDetails() {
    	CityDto slocDto = new CityDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", slocDto.getCityName());
        
        
    }
    
  
    @Test
    void testConstructor() {
    	CityDto slocDto = new CityDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", slocDto.getCityName());
         assertEquals(1, slocDto.getId());
    }

}

