package com.utcl.ccnfservice.service.master;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.repo.AgencyRepo;
import com.utcl.ccnfservice.master.service.AgencyService;
import com.utcl.dto.ccnf.AgencyDto;
import java.lang.Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

public final class TestAgencyServiceV {
  private AgencyService agencyService;

  private ModelMapper modelMapper;

  private AgencyRepo agencyRepo;

  private AgencyRepo agencyRepo1;

  private AgencyRepo agencyRepo2;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    modelMapper = Mockito.mock(ModelMapper.class);
    agencyRepo = Mockito.mock(AgencyRepo.class);
    AgencyRepo agencyRepo1 = Mockito.mock(AgencyRepo.class);
    AgencyRepo agencyRepo2 = Mockito.mock(AgencyRepo.class);
    agencyService = new AgencyService(agencyRepo1);
    agencyService = new AgencyService(agencyRepo2);
    agencyService = new AgencyService(agencyRepo);
    injectField(agencyService, "modelMapper", modelMapper);
  }

  @Test
  public void testMethodAddAgencyDetails() throws Exception {
    // 
    Agency saveResult = objectMapper.readValue("{\"agentId\": \"0\", \"agentName\": \"string\", \"address\": \"string\", \"city\": \"string\", \"pincode\": \"string\", \"vendorCode\": \"string\", \"vendorGstNumber\": \"string\", \"vendorPan\": \"string\", \"createdDate\": \"2024-07-15T17:36:30.685847300\", \"updatedDate\": \"2024-07-15T17:36:30.685847300\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", Agency.class);
    Mockito.when(agencyRepo.save(any(Agency.class))).thenReturn(saveResult);
    // 
    // 
    saveResult = objectMapper.readValue("{\"agentId\": \"0\", \"agentName\": \"string\", \"address\": \"string\", \"city\": \"string\", \"pincode\": \"string\", \"vendorCode\": \"string\", \"vendorGstNumber\": \"string\", \"vendorPan\": \"string\", \"createdDate\": \"2024-07-15T17:36:30.677808600\", \"updatedDate\": \"2024-07-15T17:36:30.677808600\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", Agency.class);
    Mockito.when(agencyRepo.save(any(Agency.class))).thenReturn(saveResult);
    // 
    AgencyDto agencyDto = objectMapper.readValue("{}", AgencyDto.class);
    AgencyDto agencyDto0 = agencyService.addAgencyDetails(agencyDto);
    AgencyDto agencyDto0Expected = null;
    Assert.assertEquals(agencyDto0Expected, agencyDto0);
  }
}
