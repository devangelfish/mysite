package com.bitacademy.mysite.exception;

public class BoardRepositoryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoardRepositoryException() {
		super("Board Repository Exception");
	}
	
	public BoardRepositoryException(String message) {
		super(message);
	}
}
