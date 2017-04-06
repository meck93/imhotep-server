package ch.uzh.ifi.seal.soprafs17.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BaseHttpException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BaseHttpException() {
        super();
        this.httpStatus = null;
    }

    public BaseHttpException(HttpStatus httpStatus, String msg) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
