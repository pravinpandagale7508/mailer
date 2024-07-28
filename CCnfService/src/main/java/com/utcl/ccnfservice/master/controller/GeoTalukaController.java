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

import com.utcl.ccnfservice.master.entity.GeoTaluka;
import com.utcl.ccnfservice.master.service.GeoTalukaService;
import com.utcl.dto.ccnf.GeoTalukaDto;
import lombok.extern.slf4j.Slf4j;


	@RestController
	@RequestMapping("/api/geoTaluka")
	@Slf4j
	public class GeoTalukaController {
		
		private GeoTalukaService geoDistService;
		

		public GeoTalukaController(GeoTalukaService geoDistService) 
		{
			super();
			this.geoDistService = geoDistService;
		}
		
		@PostMapping("/addGeoTaluka")
		public ResponseEntity<GeoTalukaDto> addGeoTalukaDetails(@RequestBody GeoTalukaDto agencyDto) 
		{
			 return ResponseEntity.ok(geoDistService.addGeoTalukaDetails(agencyDto));

		}
		
		@GetMapping("/getGeoTalukaById/{id}")
		public GeoTaluka getGeoTalukaById(@PathVariable("id") long id) throws Exception
		{
			return geoDistService.getGeoTalukaById(id);
		}

		@GetMapping("/getGeoTalukaDetails")
		public List<GeoTaluka> getGeoTalukaDetails() 
		{
			return geoDistService.getGeoTalukaDetails();
		}
		
		@PutMapping("/updateGeoTaluka")
		public ResponseEntity<GeoTalukaDto> updateGeoTalukaDetails(@RequestBody GeoTalukaDto agencyDto) 
		{
			 return ResponseEntity.ok(geoDistService.addGeoTalukaDetails(agencyDto));

		}
        
		@PostMapping("/getGeoTalukaByGeoDistrictIds")
		public List<GeoTaluka> getGeoTalukaByGeoDistrictIds(@RequestBody Map<String,Object> data) {
			log.info("Inside getGeoTalukaByGeoDistrictIds controller");
			return geoDistService.getGeoTalukaByGeoDistrictIds((List<Long>) data.get("geoDistId"));
		}
		
		
		
	}
