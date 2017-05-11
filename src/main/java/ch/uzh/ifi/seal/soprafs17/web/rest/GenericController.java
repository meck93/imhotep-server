package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
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
		log.error("Exception raised " + baseHttpException);

		return new ResponseEntity<>(baseHttpException.getMessage(), baseHttpException.getHttpStatus());
	}

	@ExceptionHandler(BadRequestHttpException.class)
	public ResponseEntity<Object> handleHttpStatusCodeExceptions(BadRequestHttpException badRequest) {
		log.error("Exception raised " + badRequest);

		return new ResponseEntity<>(badRequest.getMessage(), badRequest.getHttpStatus());
	}

	@ExceptionHandler(value = NotFoundException.class)
	@ResponseBody
	public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
		log.error("Exception raised " + notFoundException);

		return new ResponseEntity<>(notFoundException.getMessage(), notFoundException.getHttpStatus());
	}

	// Keep this one disable for all testing purposes -> it shows more detail with this one disabled
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(Exception exception) {
		log.error("Exception raised " + exception);
	}
}