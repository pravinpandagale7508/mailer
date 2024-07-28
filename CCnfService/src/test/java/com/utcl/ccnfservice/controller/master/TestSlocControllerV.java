package com.utcl.ccnfservice.controller.master;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.controller.SlocController;
import com.utcl.ccnfservice.master.entity.Sloc;
import com.utcl.ccnfservice.master.service.SlocService;

import java.lang.Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public final class TestSlocControllerV {
  private SlocController slocController;

  private SlocService slocService;

  private SlocService slocService1;

  private SlocService slocService2;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    slocService = Mockito.mock(SlocService.class);
    SlocService slocService1 = Mockito.mock(SlocService.class);
    SlocService slocService2 = Mockito.mock(SlocService.class);
    slocController = new SlocController(slocService1);
    slocController = new SlocController(slocService2);
    slocController = new SlocController(slocService);
  }

  @Test
  public void testMethodGetSlocDetailsBySlocId() throws Exception {
    Sloc slocBySlocId = objectMapper.readValue("{\"slocId\": \"1\", \"slocName\": \"ABC\", \"distId\": \"0\", \"createdDate\": \"2024-07-15T09:59:43.054834200\", \"updatedDate\": \"2024-07-15T09:59:43.054834200\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", Sloc.class);
    Mockito.when(slocService.getSlocBySlocId(eq(0L))).thenReturn(slocBySlocId);
    Sloc sloc = slocController.getSlocDetailsBySlocId(0);
    Sloc slocExpected = null;
    Assert.assertEquals(sloc.getSlocName(), "ABC");
  }
}
