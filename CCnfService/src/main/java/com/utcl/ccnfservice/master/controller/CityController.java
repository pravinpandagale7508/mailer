package com.utcl.ccnfservice.master.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.City;
import com.utcl.ccnfservice.master.service.CityService;
import com.utcl.dto.ccnf.CityDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/city")
@Slf4j
public class CityController {

	private CityService cityService;

	public CityController(CityService cityService) {
		super();
		this.cityService = cityService;
	}

	@PostMapping("/addCity")
	public ResponseEntity<CityDto> addCityDetails(@RequestBody CityDto agencyDto) {
		return ResponseEntity.ok(cityService.addCityDetails(agencyDto));

	}

	@GetMapping("/getCityById/{id}")
	public City getCityById(@PathVariable("id") long agentId) throws Exception {
		return cityService.getCityById(agentId);
	}

	@GetMapping("/getCityDetails")
	public List<City> getCityDetails() {
		return cityService.getCityDetails();
	}

	@PutMapping("/updateCity")
	public ResponseEntity<CityDto> updateCityDetails(@RequestBody CityDto agencyDto) {
		return ResponseEntity.ok(cityService.addCityDetails(agencyDto));

	}

	@PostMapping("/getCitiesByI2TalukaIds")
	public List<City> getCitiesByI2TalukaIds(@RequestBody Map<String, Object> data) {
		log.info("Inside getCitiesByI2TalukaIds controller");
		return cityService.getCitiesByI2TalukaIds((List<Long>) data.get("i2TqId"));
	}

}
