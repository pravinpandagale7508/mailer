package com.utcl.ccnfservice.controller.master;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.controller.DepotController;
import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.service.DepotService;

import java.lang.Exception;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public final class TestDepotControllerV {
  private DepotController depotController;

  private DepotService depotService;

  private DepotService depotService1;

  private DepotService depotService2;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    depotService = Mockito.mock(DepotService.class);
    DepotService depotService1 = Mockito.mock(DepotService.class);
    DepotService depotService2 = Mockito.mock(DepotService.class);
    depotController = new DepotController(depotService1);
    depotController = new DepotController(depotService2);
    depotController = new DepotController(depotService);
  }

  @Test
  public void testMethodGetDepotDetails() throws Exception {
    // 
    List<Depot> depotDetails = objectMapper.readValue("[{\"depotId\": \"1\", \"regionId\": \"0\", \"depotName\": \"string\", \"depotDesc\": \"string\", \"createdDate\": \"2024-07-15T17:16:04.298433600\", \"updatedDate\": \"2024-07-15T17:16:04.298433600\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}]", new TypeReference<List<Depot>>(){});
    Mockito.when(depotService.getDepotDetails()).thenReturn(depotDetails);
    // 
    List<Depot> list = depotController.getDepotDetails();
    List<Depot> listExpected = null;
    Assert.assertEquals(depotDetails.size(), list.size());
    Assert.assertEquals(depotDetails.size(), list.size());
  }
}
