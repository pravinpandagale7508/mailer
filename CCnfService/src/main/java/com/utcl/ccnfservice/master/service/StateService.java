package com.utcl.ccnfservice.master.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.State;
import com.utcl.ccnfservice.master.repo.StateRepo;
import com.utcl.dto.ccnf.StateDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
 public class StateService {
	private  StateRepo stateRepo;
	private ModelMapper modelMapper=new ModelMapper();
	public StateService( StateRepo stateRepo) {
		super();
		this.stateRepo = stateRepo;
		
	
	}
	public StateDto addStateDetails(StateDto stateDto) {
    	log.info("Dto {}" , stateDto);
		State state=null;
		if (stateRepo.findById(stateDto.getId()).isPresent()) {	
			updateDetails(stateDto);
		} else {
			state = stateRepo.save(toState(stateDto));
			stateDto = toStateDto(state);
		}
		return stateDto;
	}
	
	private StateDto updateDetails(StateDto stateDto) {
		return toStateDto(stateRepo.save(toState(stateDto)));
	}

	public State toState(@Validated StateDto stateDto) {
		return modelMapper.map(stateDto, State.class);
	}
	
	public StateDto toStateDto(@Validated State stateDto) {
		return modelMapper.map(stateDto, StateDto.class);
	}
	
	
	
	public State getStateByStateId(Long id)throws Exception {
		return stateRepo.findById(id).
				orElseThrow(() -> new Exception("stateRepo not found"));
		
	}
	
	
	
	    public List<State> getStateDetails() {
	    	return stateRepo.findAll();
	     
	    }

	    public List<State> getStateDetailsByzoneIds(List<Long> zoneIds) {
			log.info("Print list Region"+zoneIds);
			return stateRepo.getStateDetailsByzoneIds(zoneIds);
	    			
		}
	  
	    
	  
	
}
