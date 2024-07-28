package com.utcl.ccnfservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

import com.utcl.ccnfservice.master.entity.Sloc;
import com.utcl.ccnfservice.master.repo.SlocRepo;
import com.utcl.ccnfservice.master.service.SlocService;
import com.utcl.dto.ccnf.SlocDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SlocServiceTest {
	
     @Mock
     private SlocRepo slocRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
     
    @InjectMocks
    private SlocService slocService;
    
    
    private SlocDto slocDto=new SlocDto();
    private Sloc sloc=new Sloc();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sloc.setSlocId(1L);
        sloc.setSlocName("ABC");
        sloc.setCreatedBy("Varsga1");
        sloc.setUpdatedBy("Varsga1");
      
        slocDto.setSlocId(1L);
        slocDto.setSlocName("ABC");
       // slocDto.setCreatedBy("Varsga1");
       // slocDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(slocDto);
    }
     @Test
     @Order(1)
     public void testAddSloc() throws Exception {
    	when(slocRepo.save(Mockito.any(Sloc.class))).thenReturn(sloc);
	    log.info("print my values slocDetails: {}", slocService.addSlocDetails(slocDto));
		SlocDto addDto = slocService.addSlocDetails(slocDto);
		log.info("log slocDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetSlocById() throws Exception {
        when(slocRepo.findById(sloc.getSlocId())).thenReturn(java.util.Optional.of(sloc));
        sloc = slocService.getSlocBySlocId(1L);
        assertEquals("ABC", sloc.getSlocName());
        assertEquals("Varsga1", sloc.getCreatedBy());
        assertEquals("Varsga1", sloc.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllSlocs() throws Exception {
    	List<Sloc> slocList=new ArrayList<Sloc>();
    	slocList.add(sloc);
        when(slocRepo.findAll()).thenReturn((slocList));
        List<Sloc> agcList=slocService.getSlocDetails();
        assertEquals(agcList, slocList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
