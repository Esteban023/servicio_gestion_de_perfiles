package com.worklink.profile_service.services;

import com.worklink.profile_service.DTOS.ReviewDTO;
import com.worklink.profile_service.model.Cliente;
import com.worklink.profile_service.model.Proveedor;
import com.worklink.profile_service.model.Review;
import com.worklink.profile_service.repository.ReviewRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewServices {

    private ReviewRepository repository;
    private ServicioPerfilCliente servicioCliente;
    private ServicioPerfilServidor servicioProveedor;

    public ReviewServices(ReviewRepository repository, ServicioPerfilServidor servicioProveedor, ServicioPerfilCliente servicioCliete){
        this.repository = repository;
        this.servicioCliente = servicioCliete;
        this.servicioProveedor = servicioProveedor;

    }

    public Optional<Review> obtenerPorId(Long id){
        return repository.findById(id);
    }

    public List<Review> obtenerPorClienteId(Long idCliente){
        return repository.findByClienteId(idCliente);
    }

    public List<Review> obtenerPorProveedorId(Long idProveedor){
        return repository.findByProveedorId(idProveedor);
    }

    public Review crearReview(ReviewDTO dto){
        Review review = null;
        Optional<Proveedor> proveedorOpt = servicioProveedor.obtenerPerfilServidorPorId(dto.getIdProveedor());
        Optional<Cliente> clienteOpt = servicioCliente.obtenerPerfilClientePorId(dto.getIdCliente());
        if(proveedorOpt.isEmpty() || clienteOpt.isEmpty()) return review;
        review = new Review();
        review.setComentario(dto.getComentario());
        review.setCalificacion(dto.getCalificacion());
        review.setCliente(clienteOpt.get());
        review.setProveedor(proveedorOpt.get());

        return repository.save(review);
    }
}
