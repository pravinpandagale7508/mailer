package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.dto.ccnf.RegionDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RegionDtoTest {

    @Test
    void testGetRegionName() {
    	Region region = new Region();
    	region.setRegionName("ABC");
        assertEquals("ABC", region.getRegionName());
    }
    
    @Test
    void testGetRegionDetails() {
    	RegionDto regionDto = new RegionDto(1L, "ABC", null,null,"Varsha", "Varsha");
        assertEquals("ABC", regionDto.getRegionName());
        assertEquals("Varsha", regionDto.getCreatedBy());
        assertEquals("Varsha", regionDto.getUpdatedBy());
        
    }
    
  
    @Test
    void testConstructor() {
    	RegionDto regionDto = new RegionDto(1L, "ABC", null,null,"Varsha", "Varsha");
         assertEquals("ABC", regionDto.getRegionName());
         assertEquals(1, regionDto.getRegionId());
    }

}

