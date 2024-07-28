package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.I2Taluka;
import com.utcl.dto.ccnf.I2TalukaDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class I2TalukaDtoTest {

    @Test
    void testGetI2TalukaName() {
    	I2Taluka taluka = new I2Taluka();
    	taluka.setTalukaName("ABC");
        assertEquals("ABC", taluka.getTalukaName());
    }
    
    @Test
    void testGetI2TalukaDetails() {
    	I2TalukaDto talukaDto = new I2TalukaDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", talukaDto.getTalukaName());
        
    }
    
  
    @Test
    void testConstructor() {
    	I2TalukaDto talukaDto = new I2TalukaDto(1L, null, "ABC", null, null, null, null, null);
        assertEquals("ABC", talukaDto.getTalukaName());
         assertEquals(1, talukaDto.getId());
    }

}

