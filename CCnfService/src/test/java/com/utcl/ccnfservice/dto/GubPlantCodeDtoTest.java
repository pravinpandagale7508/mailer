package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.GubtPlantCode;
import com.utcl.dto.ccnf.GubtPlantCodeDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GubPlantCodeDtoTest {

    @Test
    void testGubPlantCode() {
    	GubtPlantCode gubtPlantCode = new GubtPlantCode(1L, "ABC", "ABC", null, null, "Varsha", "Varsha");
    	gubtPlantCode.setGubName("ABC");
        assertEquals("ABC", gubtPlantCode.getGubName());
    }
    
    @Test
    void testGubPlantCodeDetails() {
    	GubtPlantCodeDto gubPlantCodeDto =new GubtPlantCodeDto(1L, "ABC", "ABC", null, null, "Varsha", "Varsha");
        assertEquals("ABC", gubPlantCodeDto.getGubName());
        assertEquals("Varsha", gubPlantCodeDto.getCreatedBy());
        assertEquals("Varsha", gubPlantCodeDto.getUpdatedBy());
        
    }
    
  
    @Test
    void testConstructor() {
    	GubtPlantCodeDto gubPlantCodeDto =new GubtPlantCodeDto(1L, "ABC", "ABC", null, null, "Varsha", "Varsha");
         assertEquals("ABC", gubPlantCodeDto.getGubName());
         assertEquals(1, gubPlantCodeDto.getGubId());
    }

}

