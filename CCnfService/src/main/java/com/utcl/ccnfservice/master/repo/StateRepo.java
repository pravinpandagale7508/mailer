package com.utcl.ccnfservice.master.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.State;


@Repository
public interface StateRepo extends JpaRepository<State, Long> {

	State getStateByStateCode(String formatCellValue);
	
	
	 @Query("SELECT st FROM State st WHERE st.zoneId IN (:zoneIds)")
	 List<State> getStateDetailsByzoneIds(List<Long> zoneIds);
	
}
