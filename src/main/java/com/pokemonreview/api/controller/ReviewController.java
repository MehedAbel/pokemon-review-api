package com.pokemonreview.api.controller;

import com.pokemonreview.api.dto.ReviewDTO;
import com.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    private ReviewServiceImpl reviewService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("pokemon/{pokemonId}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO, @PathVariable("pokemonId") int pokemonId) {
        return new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDTO), HttpStatus.CREATED);
    }

    @GetMapping("pokemon/{pokemonId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByPokemonId(@PathVariable("pokemonId") int pokemonId) {
        return new ResponseEntity<>(reviewService.getReviewsByPokemonId(pokemonId), HttpStatus.OK);
    }

    @GetMapping("pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("pokemonId") int pokemonId, @PathVariable("reviewId") int reviewId) {
        return new ResponseEntity<>(reviewService.getReviewById(reviewId, pokemonId), HttpStatus.OK);
    }

    @PutMapping("pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable("pokemonId") int pokemonId, @PathVariable("reviewId") int reviewId, @RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewService.updateReview(reviewId, pokemonId, reviewDTO), HttpStatus.OK);
    }

    @DeleteMapping("pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("pokemonId") int pokemonId, @PathVariable("reviewId") int reviewId) {
        reviewService.deleteReview(reviewId, pokemonId);

        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
