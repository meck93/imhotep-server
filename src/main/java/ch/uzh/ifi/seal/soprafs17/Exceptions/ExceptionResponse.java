package ch.uzh.ifi.seal.soprafs17.Exceptions;

import org.springframework.stereotype.Component;

/**
 * Created by User on 01.04.2017.
 */

@Component
public class ExceptionResponse {

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
