package com.utcl.ccnfservice.controller.master;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.controller.I2districtController;
import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.ccnfservice.master.service.I2districtService;
import com.utcl.dto.ccnf.requestResponce.DepotIds;

import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class TestI2districtControllerV {
  @InjectMocks
  private I2districtController i2districtController;

  @Mock
  private I2districtService i2districtService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testMethodGetDepotRegionDetails() throws Exception {
    // 
    List<I2district> i2districtByDepotId = objectMapper.readValue("[{\"distId\": \"0\", \"depotId\": \"0\", \"distName\": \"string\", \"distDisc\": \"string\", \"createdDate\": \"2024-07-15T17:23:37.723696400\", \"updatedDate\": \"2024-07-15T17:23:37.723696400\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}]", new TypeReference<List<I2district>>(){});
    Mockito.when(i2districtService.getI2districtByDepotId(any(ArrayList.class))).thenReturn(i2districtByDepotId);
    // 
    DepotIds data = objectMapper.readValue("{\"depotIds\":[1]}", DepotIds.class);
    List<I2district> list = i2districtController.getDepotRegionDetails(data);
    List<I2district> listExpected = null;
    Assert.assertEquals(i2districtByDepotId.size(), list.size());
  }
}
