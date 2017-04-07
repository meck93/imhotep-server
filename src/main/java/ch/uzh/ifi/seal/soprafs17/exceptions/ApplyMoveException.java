package ch.uzh.ifi.seal.soprafs17.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ApplyMoveException extends RuntimeException {

    public ApplyMoveException() {
        super();
    }

    public ApplyMoveException(String msg) {
        super(msg);
    }
}
