package com.utcl.ccnfservice.master.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="sloc")
@Data
@NoArgsConstructor
@AllArgsConstructor  
@Builder
public class Sloc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    slocId;
	private String  slocName;
	private Long    distId;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
