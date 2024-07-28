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

import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.repo.AgencyRepo;
import com.utcl.ccnfservice.master.service.AgencyService;
import com.utcl.dto.ccnf.AgencyDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AgencyServiceTest {
	
     @Mock
     private AgencyRepo agencyRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
 
     @InjectMocks
     private AgencyService agencyService;
    
    
    private AgencyDto agencyDto=new AgencyDto();
    private Agency agency=new Agency();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        agency.setAgentId(1L);
        agency.setAgentName("ABC");
        agency.setCreatedBy("Varsga1");
        agency.setUpdatedBy("Varsga1");
      
        agencyDto.setAgentId(1L);
        agencyDto.setAgentName("ABC");
        //agencyDto.setCreatedBy("Varsga1");
        //agencyDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(agencyDto);
    }
     @Test
     @Order(1)
     public void testAddAgency() throws Exception {
    	when(agencyRepo.save(Mockito.any(Agency.class))).thenReturn(agency);
	    log.info("print my values agency: {}", agencyService.addAgencyDetails(agencyDto));
		AgencyDto addDto = agencyService.addAgencyDetails(agencyDto);
		log.info("log agency: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetAgencyById() throws Exception {
        when(agencyRepo.findById(agency.getAgentId())).thenReturn(java.util.Optional.of(agency));
        agency = agencyService.getAgencyByAgentId(1L);
        assertEquals("ABC", agency.getAgentName());
        assertEquals("Varsga1", agency.getCreatedBy());
        assertEquals("Varsga1", agency.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllAgencies() throws Exception {
    	List<Agency> agencyList=new ArrayList<Agency>();
    	agencyList.add(agency);
        when(agencyRepo.findAll()).thenReturn((agencyList));
        List<Agency> agcList=agencyService.getAgencyDetails();
        assertEquals(agcList, agencyList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}
