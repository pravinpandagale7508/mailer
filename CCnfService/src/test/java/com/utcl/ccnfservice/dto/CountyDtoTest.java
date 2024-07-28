package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.County;
import com.utcl.dto.ccnf.CountyDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CountyDtoTest {

    @Test
    void testGetCountyName() {
    	County county = new County();
    	county.setCountyName("ABC");
        assertEquals("ABC", county.getCountyName());
    }
    
    @Test
    void testGetCountyDetails() {
    	CountyDto countyDto = new CountyDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", countyDto.getCountyName());
       
    }
    
  
    @Test
    void testConstructor() {
    	CountyDto countyDto = new CountyDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", countyDto.getCountyName());
         assertEquals(1, countyDto.getId());
    }

}

