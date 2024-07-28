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

import com.utcl.ccnfservice.master.entity.GeoTaluka;
import com.utcl.ccnfservice.master.repo.GeoTalukaRepo;
import com.utcl.ccnfservice.master.service.GeoTalukaService;
import com.utcl.dto.ccnf.GeoTalukaDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GeoTalukaServiceTest {
	
     @Mock
     private GeoTalukaRepo geoTalukaRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    @InjectMocks
    private GeoTalukaService geoTalukaService;
    
    
    private GeoTalukaDto geoTalukaDto=new GeoTalukaDto();
    private GeoTaluka geoTaluka=new GeoTaluka();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        geoTaluka.setId(1L);
        geoTaluka.setTalukaName("ABC");
        
        geoTalukaDto.setId(1L);
        geoTalukaDto.setTalukaName("ABC");
        //geoTalukaDto.setCreatedBy("Varsga1");
       // geoTalukaDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(geoTalukaDto);
    }
     @Test
     @Order(1)
     public void testAddGeoTaluka() throws Exception {
    	when(geoTalukaRepo.save(Mockito.any(GeoTaluka.class))).thenReturn(geoTaluka);
	    log.info("print my values agencies: {}", geoTalukaService.addGeoTalukaDetails(geoTalukaDto));
		GeoTalukaDto addDto = geoTalukaService.addGeoTalukaDetails(geoTalukaDto);
		log.info("log geoTalukaDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetGeoTalukaById() throws Exception {
        when(geoTalukaRepo.findById(geoTaluka.getId())).thenReturn(java.util.Optional.of(geoTaluka));
        geoTaluka = geoTalukaService.getGeoTalukaById(1L);
        assertEquals("ABC", geoTaluka.getTalukaName());
  
       
    }
    
    @Test
    @Order(3)
    void testGetAllGeoTalukas() throws Exception {
    	List<GeoTaluka> geoTalukaList=new ArrayList<GeoTaluka>();
    	geoTalukaList.add(geoTaluka);
        when(geoTalukaRepo.findAll()).thenReturn((geoTalukaList));
        List<GeoTaluka> agcList=geoTalukaService.getGeoTalukaDetails();
        assertEquals(agcList, geoTalukaList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
