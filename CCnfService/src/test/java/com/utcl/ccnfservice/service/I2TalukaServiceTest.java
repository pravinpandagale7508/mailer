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

import com.utcl.ccnfservice.master.entity.I2Taluka;
import com.utcl.ccnfservice.master.repo.I2TalukaRepo;
import com.utcl.ccnfservice.master.service.I2TalukaService;
import com.utcl.dto.ccnf. I2TalukaDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class I2TalukaServiceTest {
	
     @Mock
     private  I2TalukaRepo i2TalukaRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    @InjectMocks
    private  I2TalukaService i2TalukaService;
    
    
    private  I2TalukaDto i2TalukaDto=new  I2TalukaDto();
    private  I2Taluka i2Taluka=new  I2Taluka();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        i2Taluka.setId(1L);
        i2Taluka.setTalukaName("ABC");
       
        i2TalukaDto.setId(1L);
        i2TalukaDto.setTalukaName("ABC");
          when(modelMapper.map(any(), any())).thenReturn(i2TalukaDto);
    }
     @Test
     @Order(1)
     public void testAddI2Taluka() throws Exception {
    	when(i2TalukaRepo.save(Mockito.any( I2Taluka.class))).thenReturn(i2Taluka);
	    log.info("print my values agencies: {}", i2TalukaService.addI2TalukaDetails(i2TalukaDto));
		 I2TalukaDto addDto = i2TalukaService.addI2TalukaDetails(i2TalukaDto);
		log.info("log i2TalukaDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetI2TalukaById() throws Exception {
        when(i2TalukaRepo.findById(i2Taluka.getId())).thenReturn(java.util.Optional.of(i2Taluka));
        i2Taluka = i2TalukaService.getI2TalukaByDistId(1L);
        assertEquals("ABC", i2Taluka.getTalukaName());
    
       
    }
    
    @Test
    @Order(3)
    void testGetAllI2Talukas() throws Exception {
    	List< I2Taluka> i2TalukaList=new ArrayList< I2Taluka>();
    	i2TalukaList.add(i2Taluka);
        when(i2TalukaRepo.findAll()).thenReturn((i2TalukaList));
        List< I2Taluka> agcList=i2TalukaService.getI2TalukaDetails();
        assertEquals(agcList, i2TalukaList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
