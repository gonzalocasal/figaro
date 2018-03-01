package com.figaro.exception;

import org.springframework.http.HttpStatus;

public class ApiError {
 
    private HttpStatus status;
    private String message;
 
    public ApiError(HttpStatus status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}