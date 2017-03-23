package ch.uzh.ifi.seal.soprafs17.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericController {

	Logger log = LoggerFactory.getLogger(GenericController.class);

	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public void handleTransactionSystemException(Exception exception, HttpServletRequest request) {
		log.error("", exception);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(Exception exception, HttpServletRequest request) {
		log.error("", exception);
	}
}
