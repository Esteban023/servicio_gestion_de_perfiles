package com.worklink.profile_service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.worklink.profile_service.model.Proveedor;
import com.worklink.profile_service.model.Usuario;
import com.worklink.profile_service.repository.RepositorioPerfilServidor;
import com.worklink.profile_service.repository.RepositorioUsuario;
import com.worklink.profile_service.services.ServicioPerfilServidor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ServicioPerfilServidorTest {

    @Autowired
    private ServicioPerfilServidor servicio;

    @Autowired
    private RepositorioPerfilServidor repositorio;

    @Autowired
    private RepositorioUsuario usuarioRepository;

    private Usuario usuario;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setNombre("Diego");
        usuario.setApellido("Diaz");
        usuario.setTelefono("123456");

        usuarioRepository.save(usuario);

        proveedor = new Proveedor();
        proveedor.setUsuario(usuario);
        proveedor.setBiografia("Bio test");
        proveedor.setActivo(true);
        proveedor.setVerificado(false);
        proveedor.setHorarioDisponibilidad("8AM-5PM");
        proveedor.setRatingPromedio(4.8);
    }

    @Test
    void guardarPerfilServidor() {
        
        Proveedor guardado = servicio.guardarPerfilServidor(proveedor);

        assertNotNull(guardado);
        assertNotNull(guardado.getId());

        Optional<Proveedor> desdeBd =
                repositorio.findById(guardado.getId());

        assertTrue(desdeBd.isPresent());

        Proveedor proveedorBd = desdeBd.get();

        assertEquals("Bio test", proveedorBd.getBiografia());
        assertTrue(proveedorBd.isActivo());
        assertFalse(proveedorBd.isVerificado());
        assertEquals("8AM-5PM", proveedorBd.getHorarioDisponibilidad());
        assertEquals(4.8, proveedorBd.getRatingPromedio());

        assertNotNull(proveedorBd.getUsuario());
        assertEquals(usuario.getEmail(), proveedorBd.getUsuario().getEmail());
    }

    @Test
    void obtenerPerfilPorId() {

        Proveedor guardado = repositorio.save(proveedor);

        Optional<Proveedor> resultado =
                servicio.obtenerPerfilServidorPorId(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals(guardado.getId(), resultado.get().getId());
        assertEquals(usuario.getEmail(), resultado.get().getUsuario().getEmail());        
        assertEquals("Bio test", resultado.get().getBiografia());
    }

    @Test
    void obtenerPerfilPorId_noExiste() {

        Optional<Proveedor> resultado =
                servicio.obtenerPerfilServidorPorId(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerTodos() {

        repositorio.save(proveedor);

        List<Proveedor> lista = servicio.obtenerTodos();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());

        Proveedor obtenido = lista.get(0);

        assertEquals(usuario.getEmail(), obtenido.getUsuario().getEmail());
        assertEquals("Bio test", obtenido.getBiografia());
    }

    @Test
    void actualizarPerfilServidor_existente() {

        Proveedor actualizado = repositorio.save(proveedor);
        actualizado.setBiografia("Nueva bio");
        actualizado.setHorarioDisponibilidad("Lunes a viernes");

        Proveedor resultado =
                servicio.actualizarPerfilServidor("test@mail.com", actualizado);

        assertNotNull(resultado);
        assertEquals("Nueva bio", resultado.getBiografia());
        assertEquals(actualizado.getId(), resultado.getId());
        assertEquals("Nueva bio", resultado.getBiografia());
        assertTrue(resultado.isActivo());
        assertEquals("Lunes a viernes", resultado.getHorarioDisponibilidad());
        assertEquals(1, repositorio.count());
    }

    @Test
    void actualizarPerfilServidor_noExiste() {

        Proveedor actualizado = new Proveedor();

        Proveedor resultado =
                servicio.actualizarPerfilServidor("noexiste@mail.com", actualizado);

        assertNull(resultado);
    }
}
