package com.assignment.clinic.exceptions;

/**
 * 
 * @author Kavitha Custom exception to handle Not found exceptions
 *
 */

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
