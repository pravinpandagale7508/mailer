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

import com.utcl.ccnfservice.master.entity.GeoDist;
import com.utcl.ccnfservice.master.repo.GeoDistRepo;
import com.utcl.ccnfservice.master.service.GeoDistService;
import com.utcl.dto.ccnf.GeoDistDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GeoDistServiceTest {
	
     @Mock
     private GeoDistRepo geoDistRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    @InjectMocks
    private GeoDistService geoDistService;
    
    
    private GeoDistDto geoDistDto=new GeoDistDto();
    private GeoDist geoDist=new GeoDist();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        geoDist.setId(1L);
        geoDist.setDistName("ABC");
        geoDist.setCreatedBy("Varsga1");
        geoDist.setUpdatedBy("Varsga1");
      
        geoDistDto.setId(1L);
        geoDistDto.setDistName("ABC");
        //geoDistDto.setCreatedBy("Varsga1");
       // geoDistDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(geoDistDto);
    }
     @Test
     @Order(1)
     public void testAddGeoDist() throws Exception {
    	when(geoDistRepo.save(Mockito.any(GeoDist.class))).thenReturn(geoDist);
	    log.info("print my values agencies: {}", geoDistService.addGeoDistDetails(geoDistDto));
		GeoDistDto addDto = geoDistService.addGeoDistDetails(geoDistDto);
		log.info("log geoDistDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetGeoDistById() throws Exception {
        when(geoDistRepo.findById(geoDist.getId())).thenReturn(java.util.Optional.of(geoDist));
        geoDist = geoDistService.getGeoDistById(1L);
        assertEquals("ABC", geoDist.getDistName());
        assertEquals("Varsga1", geoDist.getCreatedBy());
        assertEquals("Varsga1", geoDist.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllGeoDists() throws Exception {
    	List<GeoDist> geoDistList=new ArrayList<GeoDist>();
    	geoDistList.add(geoDist);
        when(geoDistRepo.findAll()).thenReturn((geoDistList));
        List<GeoDist> agcList=geoDistService.getGeoDistDetails();
        assertEquals(agcList, geoDistList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
