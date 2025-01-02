package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(int pokemonId, ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewsByPokemonId(int pokemonId);
    ReviewDTO getReviewById(int reviewId, int pokemonId);
    ReviewDTO updateReview(int reviewId, int pokemonId, ReviewDTO reviewDTO);
    void deleteReview(int reviewId, int pokemonId);
}
