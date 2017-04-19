package ch.uzh.ifi.seal.soprafs17.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class NotFoundException extends BaseHttpException {

    private static final String NOT_FOUND = " could not be found!";

    public NotFoundException() {super(); }

    public NotFoundException(Long id) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, id + NOT_FOUND);
    }
    /*
     * Returns the Class and the Id where the Exception was thrown
     */
    public NotFoundException(Long id, String classMsg) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, classMsg + " " + id + NOT_FOUND);
    }
    /*
     * Returns the Class and the Id where the Exception was thrown
     */
    public NotFoundException(int number, String classMsg) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, classMsg + " " + number + NOT_FOUND);
    }
    /*
     * Returns the Class and the Id where the Exception was thrown
     */
    public NotFoundException(String classMsg) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, classMsg + NOT_FOUND);
    }
}