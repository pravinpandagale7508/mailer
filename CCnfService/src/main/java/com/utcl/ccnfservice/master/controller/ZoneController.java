package com.utcl.ccnfservice.master.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.Zone;
import com.utcl.ccnfservice.master.service.ZoneService;
import com.utcl.dto.ccnf.ZoneDto;

@RestController
@RequestMapping("/api/zone")

public class ZoneController {
	
	@Value("${BPD.ZONE}")
	String bpdZone;
	private ZoneService zonecService;

	public ZoneController(ZoneService zonecService) {
		super();
		this.zonecService = zonecService;
	}

	@PostMapping("/addZone")
	public ResponseEntity<ZoneDto> addZoneDetails(@RequestBody ZoneDto zoneDto) {
		return ResponseEntity.ok(zonecService.addZoneDetails(zoneDto));

	}

	@GetMapping("/getZoneById/{id}")
	public Zone getZoneDetailsByZoneId(@PathVariable("id") long id) throws Exception {
		return zonecService.getZoneByZoneId(id);
	}

	@GetMapping("/getZoneDetails")
	public List<Zone> getZoneDetails() {
		return zonecService.getZoneDetails();
	}

	@GetMapping("/getBPDZoneList")
	public List<Zone> getBPDZoneList() {
		return zonecService.getBPDZoneList(Stream.of(bpdZone.split(",", -1)).toList());
	}

	@GetMapping("/getOtherThanDPDZones")
	public List<Zone> getOtherThanDPDZones() {
		return zonecService.getOtherThanDPDZones(Stream.of(bpdZone.split(",", -1)).toList());
	}

	@PutMapping("/updateZone")
	public ResponseEntity<ZoneDto> updateZoneDetails(@RequestBody ZoneDto zoneDto) {
		return ResponseEntity.ok(zonecService.addZoneDetails(zoneDto));

	}

}
