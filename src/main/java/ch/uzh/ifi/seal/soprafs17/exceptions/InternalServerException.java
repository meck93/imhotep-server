package ch.uzh.ifi.seal.soprafs17.exceptions;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String msg) {
        super(msg);
    }

    public InternalServerException(ApplyMoveException applyMoveException){
        super(applyMoveException.getMessage());
    }

}
