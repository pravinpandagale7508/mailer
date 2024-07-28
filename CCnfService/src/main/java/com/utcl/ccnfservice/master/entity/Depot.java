package com.utcl.ccnfservice.master.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="depot")
@Data
@NoArgsConstructor
@AllArgsConstructor  
@Builder
public class Depot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    depotId;
	private Long    regionId;
	private String  depotName;
	private String  depotDesc;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;

}
