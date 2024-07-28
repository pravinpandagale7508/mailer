package com.utcl.ccnfservice.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.Agency;


@Repository
public interface AgencyRepo extends JpaRepository<Agency, Long> 
{
	Agency getAgencyByAgentId(Long agentId);
	
	@Query("SELECT ag FROM Agency ag WHERE ag.agentId IN (:agentId)")
	Agency getAgentNameByAgentId(Long agentId);
	
	@Query(nativeQuery = true,
            value = "select ag.agentName,ag.agentCode from \r\n"
            		+ "master_ccnf_cement.agency ag \r\n"
            		+ "where ag.agentName LIKE CONCAT('%',:searchText, '%') \r\n"
            		+ "or ag.agentCode LIKE CONCAT('%', :searchText, '%') ")
    public List<Object> getAllAgencyNameCodeForCCNFRMC(String searchText);
}
