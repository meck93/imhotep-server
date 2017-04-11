package ch.uzh.ifi.seal.soprafs17.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ExceptionResponse {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
