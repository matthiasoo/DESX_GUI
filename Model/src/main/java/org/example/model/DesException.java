package org.example.model;

public class DesException extends Exception {
    public DesException() {
        super();
    }

    public DesException(String message) {
        super(message);
    }

    public DesException(String message, Throwable cause) {
        super(message, cause);
    }
}
