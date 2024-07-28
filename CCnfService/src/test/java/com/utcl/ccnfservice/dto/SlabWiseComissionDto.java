package com.utcl.ccnfservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.utcl.dto.ccnf.SlabwiseCommisionDto;

public class SlabWiseComissionDto {
	@Test
	void testSlabwiseDto() {
		SlabwiseCommisionDto slabWiseComissionDto = new SlabwiseCommisionDto(1L, 50.5, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		assertEquals(50.5,slabWiseComissionDto.getMinAmount());
	}

	@Test
	void testConstructor() {
		SlabwiseCommisionDto slabWiseComissionDto = new SlabwiseCommisionDto(1L, 50.5, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		assertEquals(50.5,slabWiseComissionDto.getMinAmount());
	}
	/*
	 * @Test void testInvalidCommission() { // Assuming vendorCommission cannot be
	 * negative assertThrows(IllegalArgumentException.class, () -> new VendorDTO(2,
	 * "Vendor B","V002", -0.1)); }
	 */
}
