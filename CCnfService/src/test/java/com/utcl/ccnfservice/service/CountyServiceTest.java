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

import com.utcl.ccnfservice.master.entity.County;
import com.utcl.ccnfservice.master.repo.CountyRepo;
import com.utcl.ccnfservice.master.service.CountyService;
import com.utcl.dto.ccnf.CountyDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CountyServiceTest {
	
     @Mock
     private CountyRepo countyRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    @InjectMocks
    private CountyService countyService;
    
    
    private CountyDto countyDto=new CountyDto();
    private County county=new County();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        county.setId(1L);
        county.setCountyName("ABC");
        county.setCreatedBy("Varsga1");
        county.setUpdatedBy("Varsga1");
      
        countyDto.setId(1L);
        countyDto.setCountyName("ABC");
        //countyDto.setCreatedBy("Varsga1");
       // countyDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(countyDto);
    }
     @Test
     @Order(1)
     public void testAddCounty() throws Exception {
    	when(countyRepo.save(Mockito.any(County.class))).thenReturn(county);
	    log.info("print my values agencies: {}", countyService.addCountyDetails(countyDto));
		CountyDto addDto = countyService.addCountyDetails(countyDto);
		log.info("log countyDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetCountyById() throws Exception {
        when(countyRepo.findById(county.getId())).thenReturn(java.util.Optional.of(county));
        county = countyService.getCountyById(1L);
        assertEquals("ABC", county.getCountyName());
        assertEquals("Varsga1", county.getCreatedBy());
        assertEquals("Varsga1", county.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllCountys() throws Exception {
    	List<County> countyList=new ArrayList<County>();
    	countyList.add(county);
        when(countyRepo.findAll()).thenReturn((countyList));
        List<County> agcList=countyService.getCountyDetails();
        assertEquals(agcList, countyList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
