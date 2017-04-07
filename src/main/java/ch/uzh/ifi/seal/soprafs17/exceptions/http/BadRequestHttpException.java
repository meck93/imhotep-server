package ch.uzh.ifi.seal.soprafs17.exceptions.http;

import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BadRequestHttpException extends BaseHttpException {

    public BadRequestHttpException() {super(); }

    public BadRequestHttpException(String msg) {
        // Specifies the StatusCode for return
        super(HttpStatus.BAD_REQUEST, msg);
    }

    public BadRequestHttpException(MoveValidationException moveValidationException) {
        super(HttpStatus.BAD_REQUEST, moveValidationException.getMessage());
    }
}
