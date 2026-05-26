package com.worklink.profile_service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.worklink.profile_service.DTOS.ReviewDTO;
import com.worklink.profile_service.model.Cliente;
import com.worklink.profile_service.model.Proveedor;
import com.worklink.profile_service.model.Review;
import com.worklink.profile_service.model.Usuario;
import com.worklink.profile_service.repository.RepositorioPerfilCliente;
import com.worklink.profile_service.repository.RepositorioPerfilServidor;
import com.worklink.profile_service.repository.RepositorioUsuario;
import com.worklink.profile_service.repository.ReviewRepository;
import com.worklink.profile_service.services.ReviewServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReviewServicesTest {

    @Autowired
    private ReviewServices service;

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private RepositorioUsuario usuarioRepository;

    @Autowired
    private RepositorioPerfilCliente clienteRepository;

    @Autowired
    private RepositorioPerfilServidor proveedorRepository;

    private Usuario usuarioCliente;
    private Usuario usuarioProveedor;

    private Cliente cliente;
    private Proveedor proveedor;

    private ReviewDTO dto;

    @BeforeEach
    void setUp() {

        usuarioCliente = new Usuario();
        usuarioCliente.setEmail("cliente@test.com");
        usuarioCliente.setNombre("Carlos");
        usuarioCliente.setApellido("Perez");
        usuarioCliente.setTelefono("111111");

        usuarioRepository.save(usuarioCliente);

        usuarioProveedor = new Usuario();
        usuarioProveedor.setEmail("proveedor@test.com");
        usuarioProveedor.setNombre("Ana");
        usuarioProveedor.setApellido("Gomez");
        usuarioProveedor.setTelefono("222222");

        usuarioRepository.save(usuarioProveedor);

        cliente = new Cliente();
        cliente.setUsuario(usuarioCliente);
        cliente.setActivo(true);
        cliente.setVerificado(false);
        cliente.setOcupacion("Ingeniero");

        cliente = clienteRepository.save(cliente);

        proveedor = new Proveedor();
        proveedor.setUsuario(usuarioProveedor);
        proveedor.setActivo(true);
        proveedor.setVerificado(false);
        proveedor.setBiografia("Proveedor de prueba");

        proveedor = proveedorRepository.save(proveedor);

        dto = new ReviewDTO();
        dto.setComentario("Excelente servicio");
        dto.setCalificacion(5.0);
        dto.setIdCliente(cliente.getId());
        dto.setIdProveedor(proveedor.getId());
        dto.setIdService(10L);
    }

    @Test
    void crearReview() {

        Review review = service.crearReview(dto);

        assertNotNull(review);
        assertNotNull(review.getId());

        Optional<Review> desdeBd =
                repository.findById(review.getId());

        assertTrue(desdeBd.isPresent());

        Review reviewBd = desdeBd.get();

        assertEquals("Excelente servicio", reviewBd.getComentario());
        assertEquals(5.0, reviewBd.getCalificacion());
        assertEquals(10L, reviewBd.getServiceId());

        assertNotNull(reviewBd.getCliente());
        assertNotNull(reviewBd.getProveedor());

        assertEquals(cliente.getId(),
                reviewBd.getCliente().getId());

        assertEquals(proveedor.getId(),
                reviewBd.getProveedor().getId());

        assertEquals(1, repository.count());
    }

    @Test
    void noDeberiaCrearReviewSiClienteNoExiste() {

        dto.setIdCliente(999L);

        Review review = service.crearReview(dto);

        assertNull(review);

        assertEquals(0, repository.count());
    }

    @Test
    void noDeberiaCrearReviewSiProveedorNoExiste() {

        dto.setIdProveedor(999L);

        Review review = service.crearReview(dto);

        assertNull(review);

        assertEquals(0, repository.count());
    }

    @Test
    void obtenerPorId() {

        Review guardado = service.crearReview(dto);

        Optional<Review> resultado =
                service.obtenerPorId(guardado.getId());

        assertTrue(resultado.isPresent());

        Review obtenido = resultado.get();

        assertEquals(guardado.getId(), obtenido.getId());
        assertEquals("Excelente servicio",
                obtenido.getComentario());

        assertEquals(5.0,
                obtenido.getCalificacion());
    }

    @Test
    void obtenerPorClienteId() {

        service.crearReview(dto);

        List<Review> reviews =
                service.obtenerPorClienteId(cliente.getId());

        assertFalse(reviews.isEmpty());

        assertEquals(1, reviews.size());

        Review review = reviews.get(0);

        assertEquals(cliente.getId(),
                review.getCliente().getId());

        assertEquals("Excelente servicio",
                review.getComentario());
    }

    @Test
    void obtenerPorProveedorId() {

        service.crearReview(dto);

        List<Review> reviews =
                service.obtenerPorProveedorId(proveedor.getId());

        assertFalse(reviews.isEmpty());

        assertEquals(1, reviews.size());

        Review review = reviews.get(0);

        assertEquals(proveedor.getId(),
                review.getProveedor().getId());

        assertEquals(5.0,
                review.getCalificacion());
    }

    @Test
    void obtenerPorServiceId() {

        service.crearReview(dto);

        List<Review> reviews =
                service.obtenerPorServiceId(10L);

        assertFalse(reviews.isEmpty());

        assertEquals(1, reviews.size());

        Review review = reviews.get(0);

        assertEquals(10L,
                review.getServiceId());
    }

    @Test
    void deberiaActualizarPromedioProveedor() {

        dto.setCalificacion(4.0);
        service.crearReview(dto);

        ReviewDTO dto2 = new ReviewDTO();
        dto2.setComentario("Muy bueno");
        dto2.setCalificacion(2.0);
        dto2.setIdCliente(cliente.getId());
        dto2.setIdProveedor(proveedor.getId());
        dto2.setIdService(11L);

        service.crearReview(dto2);

        Optional<Proveedor> proveedorActualizado =
                proveedorRepository.findById(proveedor.getId());

        assertTrue(proveedorActualizado.isPresent());

        Double promedio =
                proveedorActualizado.get().getRatingPromedio();

        assertEquals(3.0, promedio);
    }
}