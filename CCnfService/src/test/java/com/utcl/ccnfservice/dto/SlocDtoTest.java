package com.utcl.ccnfservice.dto;

import org.junit.jupiter.api.Test;

import com.utcl.ccnfservice.master.entity.Sloc;
import com.utcl.dto.ccnf.SlocDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SlocDtoTest {

    @Test
    void testGetSlocName() {
    	Sloc sloc = new Sloc();
    	sloc.setSlocName("ABC");
        assertEquals("ABC", sloc.getSlocName());
    }
    
    @Test
    void testGetSlocDetails() {
    	SlocDto slocDto = new SlocDto(1L, "ABC",null, null,null,"Varsha", "Varsha");
        assertEquals("ABC", slocDto.getSlocName());
        assertEquals("Varsha", slocDto.getCreatedBy());
        assertEquals("Varsha", slocDto.getUpdatedBy());
        
    }
    
  
    @Test
    void testConstructor() {
    	SlocDto slocDto = new SlocDto(1L, "ABC",null, null,null,"Varsha", "Varsha");
        assertEquals("ABC", slocDto.getSlocName());
         assertEquals(1, slocDto.getSlocId());
    }

}

