package ch.uzh.ifi.seal.soprafs17.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * This class is a customized Exception. One can send back an error
 * code and a message corresponding to the exception.
 */

@Component
public class CustomException extends Exception{

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
