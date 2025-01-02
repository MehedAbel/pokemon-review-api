package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.PokemonDTO;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.exception.PokemonNotFoundException;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    private PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDTO createPokemon(PokemonDTO pokemonDTO) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDTO.getName());
        pokemon.setType(pokemonDTO.getType());

        Pokemon pokemonSaved = pokemonRepository.save(pokemon);

        PokemonDTO pokemonResponse = new PokemonDTO();
        pokemonResponse.setId(pokemonSaved.getId());
        pokemonResponse.setName(pokemonSaved.getName());
        pokemonResponse.setType(pokemonSaved.getType());

        return pokemonResponse;
    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemon = pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemon.getContent();
        List<PokemonDTO> content = listOfPokemon.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemon.getNumber());
        pokemonResponse.setPageSize(pokemon.getSize());
        pokemonResponse.setTotalElements(pokemon.getTotalElements());
        pokemonResponse.setTotalPages(pokemon.getTotalPages());
        pokemonResponse.setLast(pokemon.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDTO getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDTO(pokemon);
    }

    @Override
    public PokemonDTO updatePokemon(PokemonDTO pokemonDTO, int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

        pokemon.setName(pokemonDTO.getName());
        pokemon.setType(pokemonDTO.getType());

        Pokemon pokemonSaved = pokemonRepository.save(pokemon);

        return mapToDTO(pokemonSaved);
    }

    @Override
    public void deletePokemon(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        pokemonRepository.delete(pokemon);
    }

    private PokemonDTO mapToDTO(Pokemon pokemon) {
        PokemonDTO pokemonDTO = new PokemonDTO();
        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setType(pokemon.getType());

        return pokemonDTO;
    }

    private Pokemon mapToEntity(PokemonDTO pokemonDTO) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDTO.getName());
        pokemon.setType(pokemonDTO.getType());

        return pokemon;
    }
}
