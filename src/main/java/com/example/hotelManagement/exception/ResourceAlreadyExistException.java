package com.example.hotelManagement.exception;

import com.example.hotelManagement.model.ApiResponse;

public class ResourceAlreadyExistException extends Exception {
	public ResourceAlreadyExistException(ApiResponse msg) {
		super(msg.getMessage());
	}
}
