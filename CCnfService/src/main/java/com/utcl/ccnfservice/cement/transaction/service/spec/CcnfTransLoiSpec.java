package com.utcl.ccnfservice.cement.transaction.service.spec;

import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.dto.ccnf.CcnfCementCalcIterator;
import com.utcl.dto.ccnf.CcnfCementLoiIterator;
import com.utcl.dto.ccnf.UserInfo;
import com.utcl.dto.ccnf.data.AgencyData;
import com.utcl.dto.ccnf.data.AgentAndAggrData;
import com.utcl.dto.ccnf.data.CcnfCommitionData;
import com.utcl.dto.ccnf.data.CcnfDirectSalesData;
import com.utcl.dto.ccnf.data.CcnfGodownKeeperData;

public interface CcnfTransLoiSpec {
	public CCnfCementLoi processCcnfLoiAgencyData(AgencyData agencyData,CCnfCementLoi cCnfCementLoi);
	public CCnfCementLoi processCcnfAgentAndAggrData(AgentAndAggrData agentAndAggrData, CCnfCementLoi cCnfCementLoi );
	public CCnfCementLoi processCcnfCommitionData(CcnfCommitionData ccnfCommitionData, CCnfCementLoi cCnfCementLoi);
	public CCnfCementLoi processCcnfDirectSalesData(CcnfDirectSalesData ccnfDirectSalesData, CCnfCementLoi cCnfCementLoi);
	public CCnfCementLoi processCcnfGodownKeeperData(CcnfGodownKeeperData ccnfGodownKeeperData, CCnfCementLoi cCnfCementLoi);
	public CCnfCementLoi processCcnfCreatedBy(UserInfo info, CCnfCementLoi cCnfCementLoi);
	public CCnfCementLoi processCcnfUpdatedBy(UserInfo info, CCnfCementLoi cCnfCementLoi);
	public CcnfCementLoiIterator makeDecisionForLoi(int numOfAgency,int numOfState,int numOfRate);
	public CcnfCementLoiIterator makeDecisionForCalculation(int numOfAgency,int numOfState,int numOfRate,int numberOfDepos,boolean isAll);
	public CcnfCementLoiIterator makeDecisionForInvoice(int numOfAgency,int numOfState,int numOfRate,int numberOfDepos,boolean isAll);
	}
