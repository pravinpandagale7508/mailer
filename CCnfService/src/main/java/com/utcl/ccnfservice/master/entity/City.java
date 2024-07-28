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
@Table(name="city")
@Data
@NoArgsConstructor
@AllArgsConstructor  
@Builder
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    id;
	private Long    i2TqId;
	private String  cityName;
	private String  cityDesc;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}
