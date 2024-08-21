package com.esprit.pidev.exceptions;

public class RecetteNotFoundException extends RuntimeException {
    public RecetteNotFoundException( ) {
        super( );
    }
    public RecetteNotFoundException(String message) {
        super(message);
    }

}