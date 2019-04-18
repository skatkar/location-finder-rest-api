package cs.locationfinder.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cs.locationfinder.exception.LocationNotFoundException;
import cs.locationfinder.vo.ApiError;

@ControllerAdvice
@RestController
public class LocationFinderExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApiError> handleExceptions(Exception ex){
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(LocationNotFoundException.class)
	public final ResponseEntity<ApiError> handleExceptions(LocationNotFoundException ex){
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
