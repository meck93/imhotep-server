package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.exceptions.BaseHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class GenericController {

	Logger log = LoggerFactory.getLogger(GenericController.class);

	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public void handleTransactionSystemException(Exception exception) {
		log.error("{}", exception);
	}

	@ExceptionHandler(BaseHttpException.class)
	public ResponseEntity<Object> handleHttpStatusCodeExceptions(BaseHttpException baseHttpException) {
		log.error("{}", baseHttpException);
		// TODO: transform exception into JSON and return something useful to the client
		// TODO: instead of using the ResponseStatus as annotation for this method
		// TODO: create a normal response object and set the status code along the message of the exception there
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setDescription(baseHttpException.getLocalizedMessage());
		return new ResponseEntity<>(exceptionResponse, baseHttpException.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(Exception exception) {
		log.error("{}", exception);
		//TODO: return always internal server error as status code and the corresponding message
		// TODO: from the exception for any other exception which has not been caught along the requests handling...
	}

}
