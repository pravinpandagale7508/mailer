package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.GeoTaluka;
import com.utcl.dto.ccnf.GeoTalukaDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GeoTalukaDtoTest {

    @Test
    void testGetGeoTalukaName() {
    	GeoTaluka taluka = new GeoTaluka();
    	taluka.setTalukaName("ABC");
        assertEquals("ABC", taluka.getTalukaName());
    }
    
    @Test
    void testGetGeoTalukaDetails() {
    	GeoTalukaDto talukaDto = new GeoTalukaDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", talukaDto.getTalukaName());
           
    }
    
  
    @Test
    void testConstructor() {
    	GeoTalukaDto talukaDto = new GeoTalukaDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", talukaDto.getTalukaName());
         assertEquals(1, talukaDto.getId());
    }

}

