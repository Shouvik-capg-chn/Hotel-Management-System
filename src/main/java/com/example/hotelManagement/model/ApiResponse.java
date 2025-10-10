package com.example.hotelManagement.model;

public class ApiResponse {

	private String code;
	private String message;

	public ApiResponse() {
	}

	public ApiResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ApiResponse [code=" + code + ", message=" + message + "]";
	}
}
