package com.utcl.ccnfservice.master.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.Zone;
import com.utcl.ccnfservice.master.repo.ZoneRepo;
import com.utcl.dto.ccnf.ZoneDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ZoneService {
	private ZoneRepo zoneRepo;
	private ModelMapper modelMapper = new ModelMapper();

	public ZoneService(ZoneRepo zoneRepo) {
		super();
		this.zoneRepo = zoneRepo;

	}

	public ZoneDto addZoneDetails(ZoneDto zoneDto) {
		log.info("Dto {}", zoneDto);
		Zone zone = null;
		if (zoneRepo.findById(zoneDto.getId()).isPresent()) {
			updateDetails(zoneDto);
		} else {
			zone = zoneRepo.save(toZone(zoneDto));
			zoneDto = toZoneDto(zone);
		}
		return zoneDto;
	}

	private ZoneDto updateDetails(ZoneDto zoneDto) {
		return toZoneDto(zoneRepo.save(toZone(zoneDto)));
	}

	public Zone toZone(@Validated ZoneDto zoneDto) {
		return modelMapper.map(zoneDto, Zone.class);
	}

	public ZoneDto toZoneDto(@Validated Zone zoneDto) {
		return modelMapper.map(zoneDto, ZoneDto.class);
	}

	public Zone getZoneByZoneId(Long id) throws Exception {
		return zoneRepo.findById(id).orElseThrow(() -> new Exception("zoneRepo not found"));

	}

	public List<Zone> getZoneDetails() {
		return zoneRepo.findAll();

	}

	public List<Zone> getBPDZoneList(List<String>list) {
		return zoneRepo.getDPDZones(list);
	}
	public List<Zone> getOtherThanDPDZones(List<String>list) {
		return zoneRepo.getOtherThanDPDZones(list);
	}
}
