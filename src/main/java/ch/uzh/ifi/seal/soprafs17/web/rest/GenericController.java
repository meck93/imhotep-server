package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.exceptions.http.BaseHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericController {

	private final Logger log = LoggerFactory.getLogger(GenericController.class);

	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public void handleTransactionSystemException(Exception exception, HttpServletRequest request) {
		log.error("Request: " + request.getRequestURL() + " raised " + exception);
	}

	@ExceptionHandler(BaseHttpException.class)
	public ResponseEntity<Object> handleHttpStatusCodeExceptions(BaseHttpException baseHttpException) {
		log.error("Request raised " + baseHttpException);

		return new ResponseEntity<>(baseHttpException.getMessage(), baseHttpException.getHttpStatus());
	}

	@ExceptionHandler(value = NotFoundException.class)
	@ResponseBody
	public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
		log.error("Request raised " + notFoundException);

		return new ResponseEntity<>(notFoundException.getMessage(), notFoundException.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(Exception exception) {
		log.error("Request raised " + exception);
		// TODO: return always internal server error as status code and the corresponding message
		// TODO: from the exception for any other exception which has not been caught along the requests handling...
	}

}
