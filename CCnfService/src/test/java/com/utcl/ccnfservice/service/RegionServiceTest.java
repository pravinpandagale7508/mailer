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

import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.repo.RegionRepo;
import com.utcl.ccnfservice.master.service.RegionService;
import com.utcl.dto.ccnf.RegionDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class RegionServiceTest {
	
     @Mock
     private RegionRepo regionRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    
    @InjectMocks
    private RegionService regionService;
    
    
    private RegionDto regionDto=new RegionDto();
    private Region region=new Region();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        region.setRegionId(1L);
        region.setRegionName("ABC");
        region.setCreatedBy("Varsga1");
        region.setUpdatedBy("Varsga1");
      
        regionDto.setRegionId(1L);
        regionDto.setRegionName("ABC");
       // regionDto.setCreatedBy("Varsga1");
       // regionDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(regionDto);
    }
     @Test
     @Order(1)
     public void testAddRegion() throws Exception {
    	when(regionRepo.save(Mockito.any(Region.class))).thenReturn(region);
	    log.info("print my values regionDetails: {}", regionService.addRegionDetails(regionDto));
		RegionDto addDto = regionService.addRegionDetails(regionDto);
		log.info("log regionDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetRegionById() throws Exception {
        when(regionRepo.findById(region.getRegionId())).thenReturn(java.util.Optional.of(region));
        region = regionService.getRegionByRegionId(1L);
        assertEquals("ABC", region.getRegionName());
        assertEquals("Varsga1", region.getCreatedBy());
        assertEquals("Varsga1", region.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllRegions() throws Exception {
    	List<Region> regionList=new ArrayList<Region>();
    	regionList.add(region);
        when(regionRepo.findAll()).thenReturn((regionList));
        List<Region> agcList=regionService.getRegionDetails();
        assertEquals(agcList, regionList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
