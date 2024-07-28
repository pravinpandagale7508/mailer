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

import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.ccnfservice.master.repo.I2districtRepo;
import com.utcl.ccnfservice.master.service.I2districtService;
import com.utcl.dto.ccnf.I2districtDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class I2districtServiceTest {
	
     @Mock
     private I2districtRepo i2districtRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
     
    @InjectMocks
    private I2districtService i2districtService;
    
    
    private I2districtDto i2districtDto=new I2districtDto();
    private I2district i2district=new I2district();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        i2district.setDistId(1L);
        i2district.setDistName("ABC");
        i2district.setCreatedBy("Varsga1");
        i2district.setUpdatedBy("Varsga1");
      
        i2districtDto.setDistId(1L);
        i2districtDto.setDistName("ABC");
        //i2districtDto.setCreatedBy("Varsga1");
        //i2districtDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(i2districtDto);
    }
     @Test
     @Order(1)
     public void testAddI2district() throws Exception {
    	when(i2districtRepo.save(Mockito.any(I2district.class))).thenReturn(i2district);
	    log.info("print my values i2district: {}", i2districtService.addI2districtDetails(i2districtDto));
		I2districtDto addDto = i2districtService.addI2districtDetails(i2districtDto);
		log.info("log i2district: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetI2districtById() throws Exception {
        when(i2districtRepo.findById(i2district.getDistId())).thenReturn(java.util.Optional.of(i2district));
        i2district = i2districtService.getI2districtByDistId(1L);
        assertEquals("ABC", i2district.getDistName());
        assertEquals("Varsga1", i2district.getCreatedBy());
        assertEquals("Varsga1", i2district.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllI2districts() throws Exception {
    	List<I2district> i2districtList=new ArrayList<I2district>();
    	i2districtList.add(i2district);
        when(i2districtRepo.findAll()).thenReturn((i2districtList));
        List<I2district> agcList=i2districtService.getI2districtDetails();
        assertEquals(agcList, i2districtList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
