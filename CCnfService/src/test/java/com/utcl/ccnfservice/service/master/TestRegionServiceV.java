package com.utcl.ccnfservice.service.master;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.repo.RegionRepo;
import com.utcl.ccnfservice.master.service.RegionService;

import java.lang.Exception;
import java.lang.Long;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

public final class TestRegionServiceV {
  private RegionService regionService;

  private RegionRepo regionRepo;

  private ModelMapper modelMapper;

  private RegionRepo regionRepo1;

  private RegionRepo regionRepo2;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    regionRepo = Mockito.mock(RegionRepo.class);
    modelMapper = Mockito.mock(ModelMapper.class);
    RegionRepo regionRepo1 = Mockito.mock(RegionRepo.class);
    RegionRepo regionRepo2 = Mockito.mock(RegionRepo.class);
    regionService = new RegionService(regionRepo1);
    regionService = new RegionService(regionRepo2);
    regionService = new RegionService(regionRepo);
    injectField(regionService, "modelMapper", modelMapper);
  }

  @Test
  public void testMethodGetRegionByStateIds() throws Exception {
    // 
    List<Region> regionByStateIds = objectMapper.readValue("[{\"regionId\": \"0\", \"stateId\": \"0\", \"regionName\": \"string\", \"regionDesc\": \"string\", \"createdDate\": \"2024-07-12T17:50:54.938343400\", \"updatedDate\": \"2024-07-12T17:50:54.938343400\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}]", new TypeReference<List<Region>>(){});
    Mockito.when(regionRepo.getRegionByStateIds(any(ArrayList.class))).thenReturn(regionByStateIds);
    // 
    ArrayList<Long> stateIds1 = objectMapper.readValue("[1]", new TypeReference<ArrayList<Long>>(){});
    List<Region> list = regionService.getRegionByStateIds(stateIds1);
    List<Region> listExpected = null;
    Assert.assertEquals(regionByStateIds.size(), list.size());
  }
}
