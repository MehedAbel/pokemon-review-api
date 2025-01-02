package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.ReviewDTO;
import com.pokemonreview.api.exception.PokemonNotFoundException;
import com.pokemonreview.api.exception.ReviewNotFoundException;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.model.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final PokemonRepository pokemonRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDTO createReview(int pokemonId, ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon doesn't exist"));
        review.setPokemon(pokemon);

        Review reviewSaved = reviewRepository.save(review);
        return mapToDTO(reviewSaved);
    }

    @Override
    public List<ReviewDTO> getReviewsByPokemonId(int pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);

        return reviews.stream().map(review -> mapToDTO(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon doesn't exist"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review doesn't exist"));

        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review doesn't belong to this pokemon");
        }

        return mapToDTO(review);
    }

    @Override
    public ReviewDTO updateReview(int reviewId, int pokemonId, ReviewDTO reviewDTO) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon doesn't exist"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review doesn't exist"));

        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review doesn't belong to this pokemon");
        }

        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getContent());
        review.setStars(reviewDTO.getStars());

        Review updatedReview = reviewRepository.save(review);

        return mapToDTO(updatedReview);
    }

    @Override
    public void deleteReview(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon doesn't exist"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review doesn't exist"));

        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review doesn't belong to this pokemon");
        }

        reviewRepository.delete(review);
    }

    private ReviewDTO mapToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setTitle(review.getTitle());
        dto.setStars(review.getStars());

        return dto;
    }

    private Review mapToEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setContent(dto.getContent());
        review.setTitle(dto.getTitle());
        review.setStars(dto.getStars());

        return review;
    }
}
