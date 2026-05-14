package com.worklink.profile_service.controller;

import com.worklink.profile_service.DTOS.ReviewDTO;
import com.worklink.profile_service.model.Review;
import com.worklink.profile_service.services.ReviewServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    ReviewServices services;

    public ReviewController(ReviewServices services){
        this.services = services;
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        Optional<Review> reviews = services.obtenerPorId(id);
        if(reviews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("obtenerPorCliente/{idCliente}")
    public ResponseEntity<?> obtenerPorClienteId(@PathVariable Long idCliente){
        List<Review> reviews = services.obtenerPorClienteId(idCliente);
        if(reviews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviews);

    }

    @GetMapping("obtenerPorProveedor/{idProveedor}")
    public ResponseEntity<?> obtenerPorProveedorId(@PathVariable Long idProveedor){
        List<Review> reviews = services.obtenerPorProveedorId(idProveedor);
        if(reviews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarReview(@RequestBody ReviewDTO dto){
        Review review = services.crearReview(dto);
        if(review == null) return ResponseEntity.badRequest().build();
        else if(review.getId() == null) return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(review);
    }
}
