package org.example;

public class PineException extends Exception {
    public PineException() {
        super();
    }

    public PineException(String message) {
        super(message);
    }

    public PineException(String message, Throwable cause) {
        super(message, cause);
    }
}
