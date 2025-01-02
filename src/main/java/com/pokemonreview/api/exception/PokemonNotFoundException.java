package com.pokemonreview.api.exception;

public class PokemonNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public PokemonNotFoundException(String message) {
        super(message);
    }
}
