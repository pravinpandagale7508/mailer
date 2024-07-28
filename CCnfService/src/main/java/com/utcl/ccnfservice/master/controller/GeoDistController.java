package com.utcl.ccnfservice.master.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.GeoDist;
import com.utcl.ccnfservice.master.service.GeoDistService;
import com.utcl.dto.ccnf.GeoDistDto;


@RestController
@RequestMapping("/api/geoDist")
public class GeoDistController {
	
	private GeoDistService geoDistService;
	

	public GeoDistController(GeoDistService geoDistService) 
	{
		super();
		this.geoDistService = geoDistService;
	}
	
	@PostMapping("/addGeoDist")
	public ResponseEntity<GeoDistDto> addGeoDistDetails(@RequestBody GeoDistDto agencyDto) 
	{
		 return ResponseEntity.ok(geoDistService.addGeoDistDetails(agencyDto));

	}
	
	@GetMapping("/getGeoDistById/{id}")
	public GeoDist getGeoDistById(@PathVariable("id") long id) throws Exception
	{
		return geoDistService.getGeoDistById(id);
	}

	@GetMapping("/getGeoDistDetails")
	public List<GeoDist> getGeoDistDetails() 
	{
		return geoDistService.getGeoDistDetails();
	}
	
	@PutMapping("/updateGeoDist")
	public ResponseEntity<GeoDistDto> updateGeoDistDetails(@RequestBody GeoDistDto agencyDto) 
	{
		 return ResponseEntity.ok(geoDistService.addGeoDistDetails(agencyDto));

	}

	
	
	
}
