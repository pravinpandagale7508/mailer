package com.utcl.ccnfservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.utcl.dto.ccnf.CCnfLoiDto;

public class CCnfLoiDtoTest {
	@Test
	void testcCnfLoi() {
		CCnfLoiDto cCnfLoiDto = new CCnfLoiDto(1L, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		assertEquals(2L, cCnfLoiDto.getAgentId());
	}

	@Test
	void testConstructor() {
		CCnfLoiDto cCnfLoiDto = new CCnfLoiDto(1L, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		assertEquals(2L, cCnfLoiDto.getAgentId());
	}

	
}
