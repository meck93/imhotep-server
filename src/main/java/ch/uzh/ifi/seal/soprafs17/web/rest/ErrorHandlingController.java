package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.exceptions.CustomException;
import ch.uzh.ifi.seal.soprafs17.exceptions.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This controller implements the exception handling at the controller layer.
 * To throw an exception one has to add the "throws Exception" to the function
 *
 * In the function body:
 * ExceptionThrower exceptionThrower = new ExceptionThrower();
 * exceptionThrower.customExceptionThrow("YOUR MSG", HttpStatus.CHOOSE_YOUR_STATUS);
 */

@ControllerAdvice
public class ErrorHandlingController extends GenericController {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ExceptionResponse>SpecialException(CustomException customException){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCode(customException.getHttpStatus().value());
        exceptionResponse.setDescription(customException.getMessage());
        return new ResponseEntity<>(exceptionResponse, customException.getHttpStatus());
    }
}
