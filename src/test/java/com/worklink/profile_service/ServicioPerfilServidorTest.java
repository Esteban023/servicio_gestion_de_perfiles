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

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setNombre("Diego");
        usuario.setApellido("Diaz");
        usuario.setTelefono("123456");

        usuarioRepository.save(usuario);
    }

    @Test
    void guardarPerfilServidor() {

        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);
        proveedor.setBiografia("Bio test");
        proveedor.setActivo(true);
        proveedor.setVerificado(false);

        Proveedor guardado = servicio.guardarPerfilServidor(proveedor);

        assertNotNull(guardado);
        assertNotNull(guardado.getId());
    }

    @Test
    void obtenerPerfilPorId() {

        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);

        Proveedor guardado = repositorio.save(proveedor);

        Optional<Proveedor> resultado =
                servicio.obtenerPerfilServidorPorId(guardado.getId());

        assertTrue(resultado.isPresent());
    }

    @Test
    void obtenerTodos() {

        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);

        repositorio.save(proveedor);

        List<Proveedor> lista = servicio.obtenerTodos();

        assertFalse(lista.isEmpty());
    }

    @Test
    void actualizarPerfilServidor_existente() {

        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);

        repositorio.save(proveedor);

        Proveedor actualizado = new Proveedor();
        actualizado.setUsuario(usuario);
        actualizado.setBiografia("Nueva bio");
        actualizado.setActivo(true);

        Proveedor resultado =
                servicio.actualizarPerfilServidor("test@mail.com", actualizado);

        assertNotNull(resultado);
        assertEquals("Nueva bio", resultado.getBiografia());
    }

    @Test
    void actualizarPerfilServidor_noExiste() {

        Proveedor actualizado = new Proveedor();

        Proveedor resultado =
                servicio.actualizarPerfilServidor("noexiste@mail.com", actualizado);

        assertNull(resultado);
    }
}
