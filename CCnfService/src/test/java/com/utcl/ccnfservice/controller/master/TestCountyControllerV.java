package com.utcl.ccnfservice.controller.master;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.controller.CountyController;
import com.utcl.ccnfservice.master.entity.County;
import com.utcl.ccnfservice.master.service.CountyService;

import java.lang.Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public final class TestCountyControllerV {
  private CountyController countyController;

  private CountyService countyService;

  private CountyService countyService1;

  private CountyService countyService2;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    countyService = Mockito.mock(CountyService.class);
    CountyService countyService1 = Mockito.mock(CountyService.class);
    CountyService countyService2 = Mockito.mock(CountyService.class);
    countyController = new CountyController(countyService1);
    countyController = new CountyController(countyService2);
    countyController = new CountyController(countyService);
  }

  @Test
  public void testMethodGetCountyById() throws Exception {
    // 
    County countyById = objectMapper.readValue("{\"id\": \"0\", \"cityId\": \"0\", \"countyName\": \"string\", \"countyDesc\": \"string\", \"createdDate\": \"2024-07-12T18:46:44.305594300\", \"updatedDate\": \"2024-07-12T18:46:44.305594300\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", County.class);
    Mockito.when(countyService.getCountyById(eq(0L))).thenReturn(countyById);
    // 
    County county = countyController.getCountyById(0);
    County countyExpected = null;
    Assert.assertEquals(countyById.getId(), county.getId());
  }
}
