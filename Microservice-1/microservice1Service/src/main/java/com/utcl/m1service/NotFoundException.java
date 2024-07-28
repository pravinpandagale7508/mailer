package com.utcl.m1service;

public class NotFoundException extends RuntimeException {
	String msg;

	public NotFoundException(String msg) {
		super();
		this.msg = msg;
	}
	
}
