package com.utcl.ccnfservice.controller.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.controller.AgencyController;
import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.service.AgencyService;

import java.lang.Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

public final class TestAgencyControllerV {

    private AgencyController agencyController;

    private AgencyService agencyService;

    private AgencyService agencyService1;

    private AgencyService agencyService2;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() throws Exception {
        agencyService = Mockito.mock(AgencyService.class);
        AgencyService agencyService1 = Mockito.mock(AgencyService.class);
        AgencyService agencyService2 = Mockito.mock(AgencyService.class);
        agencyController = new AgencyController(agencyService1);
        agencyController = new AgencyController(agencyService2);
        agencyController = new AgencyController(agencyService);
    }

    @Test
    public void testMethodGetAgencyById() throws Exception {
        // 
        Agency agencyByAgentId = objectMapper.readValue("{\"agentId\": \"0\", \"agentName\": \"string\", \"address\": \"string\", \"city\": \"string\", \"pincode\": \"string\", \"vendorCode\": \"string\", \"vendorGstNumber\": \"string\", \"vendorPan\": \"string\", \"createdDate\": \"2024-07-12T18:33:05.893656300\", \"updatedDate\": \"2024-07-12T18:33:05.894671500\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", Agency.class);
        Mockito.when(agencyService.getAgencyByAgentId(eq(0L))).thenReturn(agencyByAgentId);
        // 
        Agency agency = agencyController.getAgencyById(0);
        Agency agencyExpected = null;
        Assert.assertEquals(agencyByAgentId.getAgentId(), agency.getAgentId());
    }

    @Test
    public void testMethodGetAgencyById1() throws Exception {
        // 
        Agency agencyByAgentId = objectMapper.readValue("{\"agentId\": \"0\", \"agentName\": \"string\", \"address\": \"string\", \"city\": \"string\", \"pincode\": \"string\", \"vendorCode\": \"string\", \"vendorGstNumber\": \"string\", \"vendorPan\": \"string\", \"createdDate\": \"2024-07-15T14:30:21.379870300\", \"updatedDate\": \"2024-07-15T14:30:21.379870300\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", Agency.class);
        Mockito.when(agencyService.getAgencyByAgentId(eq(0L))).thenReturn(agencyByAgentId);
        // 
        Agency agency = agencyController.getAgencyById(0);
        Agency agencyExpected = null;
        Assert.assertEquals(agencyExpected, agency);
    }
}
