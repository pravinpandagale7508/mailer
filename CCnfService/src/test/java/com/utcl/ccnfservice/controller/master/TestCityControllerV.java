package com.utcl.ccnfservice.controller.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcl.ccnfservice.master.controller.CityController;
import com.utcl.ccnfservice.master.entity.City;
import com.utcl.ccnfservice.master.service.CityService;

import java.lang.Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;
import com.utcl.dto.ccnf.CityDto;
import org.springframework.http.ResponseEntity;

public final class TestCityControllerV {

    private CityController cityController;

    private CityService cityService;

    private CityService cityService1;

    private CityService cityService2;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() throws Exception {
        cityService = Mockito.mock(CityService.class);
        CityService cityService1 = Mockito.mock(CityService.class);
        CityService cityService2 = Mockito.mock(CityService.class);
        cityController = new CityController(cityService1);
        cityController = new CityController(cityService2);
        cityController = new CityController(cityService);
        injectField(cityController, "cityService", cityService);
    }

    @Test
    public void testMethodGetCityById() throws Exception {
        // 
        City cityById = objectMapper.readValue("{\"id\": \"0\", \"i2TqId\": \"0\", \"cityName\": \"string\", \"cityDesc\": \"string\", \"createdDate\": \"2024-07-15T15:02:10.981616900\", \"updatedDate\": \"2024-07-15T15:02:10.981616900\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", City.class);
        Mockito.when(cityService.getCityById(eq(0L))).thenReturn(cityById);
        // 
        City city = cityController.getCityById(0);
        City cityExpected = null;
        Assert.assertEquals(cityExpected, city);
    }

    @Test
    public void testMethodAddCityDetails() throws Exception {
        // 
        CityDto addCityDetailsResult = objectMapper.readValue("{\"id\": \"0\", \"i2TqId\": \"0\", \"cityName\": \"string\", \"cityDesc\": \"string\", \"createdDate\": \"2024-07-16T11:58:59.297040400\", \"updatedDate\": \"2024-07-16T11:58:59.297040400\", \"createdBy\": \"string\", \"updatedBy\": \"string\"}", CityDto.class);
        Mockito.when(cityService.addCityDetails(any(CityDto.class))).thenReturn(addCityDetailsResult);
        // 
        CityDto agencyDto = objectMapper.readValue("{}", CityDto.class);
        ResponseEntity<CityDto> responseEntity = cityController.addCityDetails(agencyDto);
        ResponseEntity<CityDto> responseEntityExpected = null;
        Assert.assertEquals(responseEntityExpected, responseEntity);
    }
}
