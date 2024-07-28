package com.utcl.ccnfservice.rmc.transaction.entity;

import java.util.Date;
import java.util.List;

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
@Table(name="ccnfrmcexpense")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CcnfRMCExpense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String  type;
	private Double maxDataValue;
	private Double shairingPer;
	
}
