package ch.uzh.ifi.seal.soprafs17.Exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by User on 01.04.2017.
 */
public class ExceptionThrower {

    public void customExceptionThrow(String errorMessage, HttpStatus httpStatus) throws CustomException{
        CustomException customException = new CustomException();
        customException.setCode(999);
        customException.setMessage(errorMessage);
        customException.setHttpStatus(httpStatus);
        throw customException;
    }
}
