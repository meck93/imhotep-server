package ch.uzh.ifi.seal.soprafs17.exceptions;

import org.springframework.stereotype.Component;

@Component
public class MoveValidationException extends RuntimeException {

    public MoveValidationException(){super(); }

    public MoveValidationException(String msg) {
        super(msg);
    }

}
