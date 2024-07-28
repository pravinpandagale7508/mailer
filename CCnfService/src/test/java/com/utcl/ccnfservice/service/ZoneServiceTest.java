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

import com.utcl.ccnfservice.master.entity.Zone;
import com.utcl.ccnfservice.master.repo.ZoneRepo;
import com.utcl.ccnfservice.master.service.ZoneService;
import com.utcl.dto.ccnf.ZoneDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ZoneServiceTest {
	
     @Mock
     private ZoneRepo zoneRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    @InjectMocks
    private ZoneService zoneService;
    
    
    private ZoneDto zoneDto=new ZoneDto();
    private Zone zone=new Zone();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        zone.setId(1L);
        zone.setZoneCode("ABC");
        zoneDto.setId(1L);
        zoneDto.setZoneCode("ABC");
         when(modelMapper.map(any(), any())).thenReturn(zoneDto);
    }
     @Test
     @Order(1)
     public void testAddZone() throws Exception {
    	when(zoneRepo.save(Mockito.any(Zone.class))).thenReturn(zone);
	    log.info("print my values agencies: {}", zoneService.addZoneDetails(zoneDto));
		ZoneDto addDto = zoneService.addZoneDetails(zoneDto);
		log.info("log zoneDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetZoneById() throws Exception {
        when(zoneRepo.findById(zone.getId())).thenReturn(java.util.Optional.of(zone));
        zone = zoneService.getZoneByZoneId(1L);
        assertEquals("ABC", zone.getZoneCode());
  
       
    }
    
    @Test
    @Order(3)
    void testGetAllZones() throws Exception {
    	List<Zone> zoneList=new ArrayList<Zone>();
    	zoneList.add(zone);
        when(zoneRepo.findAll()).thenReturn((zoneList));
        List<Zone> agcList=zoneService.getZoneDetails();
        assertEquals(agcList, zoneList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
