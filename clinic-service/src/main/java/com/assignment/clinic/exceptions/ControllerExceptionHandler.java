package com.assignment.clinic.exceptions;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * 
 * @author Kavitha - Common Exceptions
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	
	/**
	 * 
	 * @param ex NumberFormatException
	 * @param request Description for the exception message
	 * @return Http 400 Bad request code
	 */
	 @ExceptionHandler(NumberFormatException.class)
	    public ResponseEntity<ErrorMessage> numberFormatExceptionHandler(NumberFormatException exception, WebRequest request) {
		 ErrorMessage response = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), exception.getMessage(),
					request.getDescription(false));
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	
	/**
	 * Exception 
	 * @param ex  ResourceNotFoundException
	 * @param request Description for the exception message
	 * @return Http 404 Not found code
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Exception handles errors related to validation
	 * @param ex  MethodArgumentNotValidException thrown for validation failures
	 * @param request Description for the exception message
	 * @return Http 400 Bad request code
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Exception handles errors related to data integrity
	 * @param ex  MethodArgumentNotValidException thrown for data integrity issues
	 * @param request Description for the exception message
	 * @return Http 400 Bad request code
	 */
	@ExceptionHandler(value = {DataIntegrityViolationException.class})
	public ResponseEntity<ErrorMessage> handlePreconditionFailed(DataIntegrityViolationException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}
	/**
	 * Exception common exception
	 * @param ex  
	 * @param request Description for the exception message
	 * @return Http 500 internal server error code
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
