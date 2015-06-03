package org.bl.json.jersey.model.errors;

import java.util.Map;

/**
 * @author Bogdan Lupashko
 */

public class ErrorString {
    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
