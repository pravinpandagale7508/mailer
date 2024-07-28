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
@Table(name="agency")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    agentId;
	private String  agentName;
	private String  agentCode;
	private String  address;
	private String	city;
	private String	pincode;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
