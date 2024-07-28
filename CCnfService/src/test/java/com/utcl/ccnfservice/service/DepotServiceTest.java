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

import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.repo.DepotRepo;
import com.utcl.ccnfservice.master.service.DepotService;
import com.utcl.dto.ccnf.DepotDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DepotServiceTest {
	
     @Mock
     private DepotRepo depotRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    @InjectMocks
    private DepotService depotService;
    
    
    private DepotDto depotDto=new DepotDto();
    private Depot depot=new Depot();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        depot.setDepotId(1L);
        depot.setDepotName("ABC");
        depot.setCreatedBy("Varsga1");
        depot.setUpdatedBy("Varsga1");
      
        depotDto.setDepotId(1L);
        depotDto.setDepotName("ABC");
        //depotDto.setCreatedBy("Varsga1");
       // depotDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(depotDto);
    }
     @Test
     @Order(1)
     public void testAddDepot() throws Exception {
    	when(depotRepo.save(Mockito.any(Depot.class))).thenReturn(depot);
	    log.info("print my values agencies: {}", depotService.addDepotDetails(depotDto));
		DepotDto addDto = depotService.addDepotDetails(depotDto);
		log.info("log depotDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetDepotById() throws Exception {
        when(depotRepo.findById(depot.getDepotId())).thenReturn(java.util.Optional.of(depot));
        depot = depotService.getDepotByDepotId(1L);
        assertEquals("ABC", depot.getDepotName());
        assertEquals("Varsga1", depot.getCreatedBy());
        assertEquals("Varsga1", depot.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllDepots() throws Exception {
    	List<Depot> depotList=new ArrayList<Depot>();
    	depotList.add(depot);
        when(depotRepo.findAll()).thenReturn((depotList));
        List<Depot> agcList=depotService.getDepotDetails();
        assertEquals(agcList, depotList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
