package com.pokemonreview.api.controller;

import com.pokemonreview.api.dto.PokemonDTO;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.service.PokemonService;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController {
    private PokemonServiceImpl pokemonService;

    @Autowired
    public PokemonController(PokemonServiceImpl pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("pokemon")
    public ResponseEntity<PokemonResponse> getAllPokemon(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<PokemonDTO> pokemonDetail(@PathVariable("id") int id) {
        return new ResponseEntity<>(pokemonService.getPokemonById(id), HttpStatus.OK);
    }

    @PostMapping("pokemon")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDTO> createPokemon(@RequestBody PokemonDTO pokemonDTO) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDTO), HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}")
    public ResponseEntity<PokemonDTO> updatePokemon(@PathVariable("id") int id, @RequestBody PokemonDTO pokemonDTO) {
        PokemonDTO response = pokemonService.updatePokemon(pokemonDTO, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("pokemon/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id) {
        pokemonService.deletePokemon(id);
        return new ResponseEntity<>("Pokemon deleted", HttpStatus.OK);
    }
}
