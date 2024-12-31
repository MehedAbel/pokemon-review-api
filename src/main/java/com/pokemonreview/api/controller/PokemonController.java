package com.pokemonreview.api.controller;

import com.pokemonreview.api.dto.PokemonDTO;
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
    public ResponseEntity<List<Pokemon>> getPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();

        pokemons.add(new Pokemon(1, "Squirtle", "Water"));
        pokemons.add(new Pokemon(2, "Pikachu", "Electric"));
        pokemons.add(new Pokemon(3, "Charmander", "Fire"));

        return ResponseEntity.ok(pokemons);
    }

    @GetMapping("pokemon/{id}")
    public Pokemon pokemonDetail(@PathVariable("id") int id) {
        return new Pokemon(id, "Squirtle", "Water");
    }

    @PostMapping("pokemon")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDTO> createPokemon(@RequestBody PokemonDTO pokemonDTO) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDTO), HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}")
    public ResponseEntity<Pokemon> updatePokemon(@PathVariable("id") int id, @RequestBody Pokemon pokemon) {
        return ResponseEntity.ok(pokemon);
    }

    @DeleteMapping("pokemon/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id) {
        return ResponseEntity.ok("Pokemon deleted successfully");
    }
}
