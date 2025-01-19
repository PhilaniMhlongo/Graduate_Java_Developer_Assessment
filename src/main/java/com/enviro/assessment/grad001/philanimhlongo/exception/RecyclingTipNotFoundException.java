package com.enviro.assessment.grad001.philanimhlongo.exception;

public class RecyclingTipNotFoundException extends RuntimeException {
    public RecyclingTipNotFoundException(String message) {
        super(message);
    }

    public RecyclingTipNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecyclingTipNotFoundException(Throwable cause) {
        super(cause);
    }
    
}
