package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.dto.ccnf.I2districtDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class I2districtDtoTest {

    @Test
    void testI2districtName() {
    	I2district i2district = new I2district();
    	i2district.setDistName("ABC");
        assertEquals("ABC", i2district.getDistName());
    }
    
    @Test
    void testI2districtDetails() {
    	I2districtDto i2districtDto = new I2districtDto(1L, "ABC",null, null,null,"Varsha", "Varsha");
        assertEquals("ABC", i2districtDto.getDistName());
        assertEquals("Varsha", i2districtDto.getCreatedBy());
        assertEquals("Varsha", i2districtDto.getUpdatedBy());
        
    }
    
  
    @Test
    void testConstructor() {
    	I2districtDto i2districtDto = new I2districtDto(1L, "ABC",null, null,null,"Varsha", "Varsha");
        assertEquals("ABC", i2districtDto.getDistName());
         assertEquals(1, i2districtDto.getDistId());
    }

}

