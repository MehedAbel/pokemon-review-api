package com.pokemonreview.api.exception;

public class ReviewNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
