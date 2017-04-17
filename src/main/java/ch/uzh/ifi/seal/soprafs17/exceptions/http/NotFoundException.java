package ch.uzh.ifi.seal.soprafs17.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class NotFoundException extends BaseHttpException {

    private Long Id;

    public NotFoundException() {super(); }

    public NotFoundException(Long Id) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, Id + ": could not be found!");
        this.Id = Id;
    }
    /*
     * Returns the Class and the Id where the Exception was thrown
     */
    public NotFoundException(Long Id, String classMsg) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, classMsg + " " + Id + " could not be found!");
        this.Id = Id;
    }
    /*
     * Returns the Class and the Id where the Exception was thrown
     */
    public NotFoundException(int number, String classMsg) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, classMsg + " " + number + " could not be found!");
        this.Id = Long.parseLong(number+"");
    }
    /*
     * Returns the Class and the Id where the Exception was thrown
     */
    public NotFoundException(String classMsg) {
        // Specifies the StatusCode for return
        super(HttpStatus.NOT_FOUND, classMsg + " could not be found!");
    }
}