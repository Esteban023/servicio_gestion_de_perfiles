package com.worklink.profile_service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.worklink.profile_service.model.Cliente;
import com.worklink.profile_service.model.Usuario;
import com.worklink.profile_service.repository.RepositorioPerfilCliente;
import com.worklink.profile_service.repository.RepositorioUsuario;
import com.worklink.profile_service.services.ServicioPerfilCliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ServicioPerfilClienteTest {

    @Autowired
    private ServicioPerfilCliente servicio;

    @Autowired
    private RepositorioPerfilCliente repositorio;

    @Autowired
    private RepositorioUsuario usuarioRepository;

    private Usuario usuario;
    private Cliente cliente;

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setEmail("cliente@test.com");
        usuario.setNombre("Diego");
        usuario.setApellido("Diaz");
        usuario.setTelefono("123456");

        usuarioRepository.save(usuario);

        cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setActivo(true);
        cliente.setVerificado(false);
        cliente.setRatingPromedio(4.5);
        cliente.setOcupacion("Ingeniero");
    }

    @Test
    void guardarPerfilCliente() {

        Cliente guardado = servicio.guardarPerfilCliente(cliente);

        assertNotNull(guardado);
        assertNotNull(guardado.getId());

        Optional<Cliente> desdeBd =
                repositorio.findById(guardado.getId());

        assertTrue(desdeBd.isPresent());

        Cliente clienteBd = desdeBd.get();

        assertEquals("Ingeniero", clienteBd.getOcupacion());
        assertEquals(4.5, clienteBd.getRatingPromedio());
        assertTrue(clienteBd.isActivo());
        assertFalse(clienteBd.isVerificado());

        assertNotNull(clienteBd.getUsuario());
        assertEquals(usuario.getEmail(), clienteBd.getUsuario().getEmail());
    }

    @Test
    void obtenerPerfilCliente() {

        Cliente guardado = repositorio.save(cliente);

        Optional<Cliente> resultado =
                servicio.obtenerPerfilCliente(usuario.getEmail());

        assertTrue(resultado.isPresent());

        Cliente obtenido = resultado.get();

        assertEquals(guardado.getId(), obtenido.getId());
        assertEquals(usuario.getEmail(), obtenido.getUsuario().getEmail());
        assertEquals("Ingeniero", obtenido.getOcupacion());
        assertEquals(4.5, obtenido.getRatingPromedio());
    }

    @Test
    void obtenerTodosLosPerfilesClientes() {

        repositorio.save(cliente);

        List<Cliente> lista =
                servicio.obtenerTodosLosPerfilesClientes();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());

        Cliente obtenido = lista.get(0);

        assertEquals(usuario.getEmail(), obtenido.getUsuario().getEmail());
        assertEquals("Ingeniero", obtenido.getOcupacion());
        assertEquals(4.5, obtenido.getRatingPromedio());
    }

    @Test
    void actualizarPerfilCliente_existente() {

        Cliente guardado = repositorio.save(cliente);

        guardado.setOcupacion("Arquitecto");
        guardado.setRatingPromedio(5.0);
        guardado.setVerificado(true);

        Cliente resultado =
                servicio.actualizarPerfilCliente(
                        usuario.getEmail(),
                        guardado
                );

        assertNotNull(resultado);

        assertEquals(guardado.getId(), resultado.getId());
        assertEquals("Arquitecto", resultado.getOcupacion());
        assertEquals(5.0, resultado.getRatingPromedio());
        assertTrue(resultado.isVerificado());
        assertTrue(resultado.isActivo());

        assertEquals(1, repositorio.count());
    }

    @Test
    void actualizarPerfilCliente_noExiste() {

        Cliente actualizado = new Cliente();

        Cliente resultado =
                servicio.actualizarPerfilCliente(
                        "noexiste@test.com",
                        actualizado
                );

        assertNull(resultado);
    }

    @Test
    void eliminarPerfilCliente() {

        repositorio.save(cliente);

        assertEquals(1, repositorio.count());

        servicio.eliminarPerfilCliente(usuario.getEmail());

        assertEquals(0, repositorio.count());

        Optional<Cliente> resultado =
                servicio.obtenerPerfilCliente(usuario.getEmail());

        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerPerfilClientePorId() {

        Cliente guardado = repositorio.save(cliente);

        Optional<Cliente> resultado =
                servicio.obtenerPerfilClientePorId(guardado.getId());

        assertTrue(resultado.isPresent());

        Cliente obtenido = resultado.get();

        assertEquals(guardado.getId(), obtenido.getId());
        assertEquals(usuario.getEmail(), obtenido.getUsuario().getEmail());
        assertEquals("Ingeniero", obtenido.getOcupacion());
    }
}
