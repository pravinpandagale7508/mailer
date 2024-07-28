package com.utcl.ccnfservice.master.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.repo.DepotRepo;
import com.utcl.dto.ccnf.DepotDto;

import lombok.extern.slf4j.Slf4j;

	@Service
	@Slf4j
  public class DepotService {
		private DepotRepo depotRepo;
		private ModelMapper modelMapper=new ModelMapper();

		public DepotService( DepotRepo depotRepo) {
			super();
			this.depotRepo = depotRepo;
		
		}

		public DepotDto addDepotDetails(DepotDto depotDto) {
	    	log.info("Dto {}" , depotDto);
			Depot depot=null;
			if (depotRepo.findById(depotDto.getDepotId()).isPresent()) {	
				updateDetails(depotDto);
			} else {
				depot = depotRepo.save(toDepot(depotDto));
				depotDto = toDepotDto(depot);

			}
			return depotDto;
		}
		private DepotDto updateDetails(DepotDto depotDto) {
			return toDepotDto(depotRepo.save(toDepot(depotDto)));

		}
		public Depot toDepot(@Validated DepotDto depotDto) {
			return modelMapper.map(depotDto, Depot.class);
		}
		
		public DepotDto toDepotDto(@Validated Depot depot) {
			return modelMapper.map(depot, DepotDto.class);
		}
		
		
		
		public Depot getDepotByDepotId(Long depotId) throws Exception{
			return depotRepo.findById(depotId).
	    			orElseThrow(() -> new Exception("Depot not found"));
		}
		
		 public List<Depot> getDepotDetails() {
		    	return depotRepo.findAll();
		        
		    }
		
		public List<Depot> getDepotDetailsByRegionId(List<Long> regionIds) {
			log.info("Print list"+regionIds);
			return depotRepo.getDepotDetailsByRegionId(regionIds);
	    			
		}
		

		public List<String> getDepotNameByDepotId(List<Long> depotIds) throws Exception {
			List<String> depotNames=new ArrayList<>();
			List<Depot> depotlist=	depotRepo.getDepotNameBydepotIds(depotIds);
			for(Depot depo: depotlist) {
		    	 depotNames.add(depo.getDepotName());
			}
		   return depotNames;
		}

	}


