package com.worklink.profile_service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.worklink.profile_service.DTOS.horarios.ActualizarEstadosSlotsRequest;
import com.worklink.profile_service.DTOS.horarios.AñadirHorarioPorRangoRequest;
import com.worklink.profile_service.DTOS.horarios.CrearHorariosRequest;
import com.worklink.profile_service.DTOS.horarios.HorariosPorFechaRequest;
import com.worklink.profile_service.DTOS.horarios.HorasDispDto;
import com.worklink.profile_service.DTOS.horarios.ReservasSlotsRequest;
import com.worklink.profile_service.model.HorariosSlots;
import com.worklink.profile_service.model.Proveedor;
import com.worklink.profile_service.model.Usuario;
import com.worklink.profile_service.repository.HorariosSlotsRepository;
import com.worklink.profile_service.repository.RepositorioPerfilServidor;
import com.worklink.profile_service.repository.RepositorioUsuario;
import com.worklink.profile_service.services.HorariosSlotsServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class HorariosSlotsServicesTest {

    @Autowired
    private HorariosSlotsServices service;

    @Autowired
    private HorariosSlotsRepository repository;

    @Autowired
    private RepositorioPerfilServidor proveedorRepository;

    @Autowired
    private RepositorioUsuario usuarioRepository;

    private Usuario usuario;
    private Proveedor proveedor;
    private HorariosSlots slot;

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setEmail("proveedor@test.com");
        usuario.setNombre("Diego");
        usuario.setApellido("Diaz");
        usuario.setTelefono("123456");

        usuarioRepository.save(usuario);

        proveedor = new Proveedor();
        proveedor.setUsuario(usuario);
        proveedor.setActivo(true);
        proveedor.setVerificado(true);
        proveedor.setBiografia("Proveedor test");

        proveedor = proveedorRepository.save(proveedor);

        slot = new HorariosSlots(
                LocalDate.now(),
                LocalTime.of(8, 0),
                proveedor
        );
    }

    @Test
    void guardarSlot() {

        HorariosSlots guardado = service.guardarSlot(slot);

        assertNotNull(guardado);
        assertNotNull(guardado.getId());

        HorariosSlots desdeBd =
                repository.findById(guardado.getId()).orElse(null);

        assertNotNull(desdeBd);

        assertEquals(LocalDate.now(), desdeBd.getFecha());
        assertEquals(LocalTime.of(8, 0), desdeBd.getHoraInicio());
        assertEquals(LocalTime.of(8, 30), desdeBd.getHoraFin());

        assertEquals("Disponible", desdeBd.getEstado());

        assertNotNull(desdeBd.getProveedor());
        assertEquals(
                proveedor.getId(),
                desdeBd.getProveedor().getId()
        );
    }

    @Test
    void crearSlots() {

        CrearHorariosRequest dto = new CrearHorariosRequest();

        dto.setIdProveedor(proveedor.getId());

        dto.setDiasLaborales(
                List.of(
                        DayOfWeek.MONDAY.getValue()
                )
        );

        dto.setHoraInicio(LocalTime.of(8, 0));
        dto.setHoraFin(LocalTime.of(9, 0));

        List<HorariosSlots> slots =
                service.crearSlots(dto);

        assertNotNull(slots);
        assertFalse(slots.isEmpty());

        HorariosSlots primerSlot = slots.get(0);

        assertEquals("Disponible", primerSlot.getEstado());

        assertEquals(
                proveedor.getId(),
                primerSlot.getProveedor().getId()
        );

        assertNotNull(primerSlot.getId());
    }

    @Test
    void crearSlots_proveedorNoExiste() {

        CrearHorariosRequest dto = new CrearHorariosRequest();

        dto.setIdProveedor(999L);

        dto.setDiasLaborales(
                List.of(
                        DayOfWeek.MONDAY.getValue()
                )
        );

        dto.setHoraInicio(LocalTime.of(8, 0));
        dto.setHoraFin(LocalTime.of(9, 0));

        List<HorariosSlots> resultado =
                service.crearSlots(dto);

        assertNull(resultado);
    }

    @Test
    void añadirSlotsPorRango() {

        AñadirHorarioPorRangoRequest dto =
                new AñadirHorarioPorRangoRequest();

        dto.setIdProveedor(proveedor.getId());

        dto.setFechaInicio(LocalDate.now());
        dto.setFechaFin(LocalDate.now().plusDays(1));

        dto.setHoraInicio(LocalTime.of(8, 0));
        dto.setHoraFin(LocalTime.of(9, 0));

        List<HorariosSlots> slots =
                service.añadirSlotsPorRango(dto);

        assertNotNull(slots);
        assertFalse(slots.isEmpty());

        HorariosSlots slotBd = slots.get(0);

        assertEquals("Disponible", slotBd.getEstado());

        assertEquals(
                proveedor.getId(),
                slotBd.getProveedor().getId()
        );
    }

    @Test
    void getAll() {

        repository.save(slot);

        List<HorariosSlots> lista =
                service.getAll(proveedor.getId());

        assertFalse(lista.isEmpty());

        assertEquals(1, lista.size());

        HorariosSlots obtenido = lista.get(0);

        assertEquals(
                proveedor.getId(),
                obtenido.getProveedor().getId()
        );

        assertEquals(
                LocalTime.of(8, 0),
                obtenido.getHoraInicio()
        );
    }

    @Test
    void getFechasDisponibles() {

        repository.save(slot);

        List<LocalDate> fechas =
                service.getFechasDisponibles(proveedor.getId());

        assertFalse(fechas.isEmpty());

        assertTrue(
                fechas.contains(LocalDate.now())
        );
    }

    @Test
    void getHorariosPorFecha() {

        HorariosSlots slot1 =
                new HorariosSlots(
                        LocalDate.now(),
                        LocalTime.of(8, 0),
                        proveedor
                );

        HorariosSlots slot2 =
                new HorariosSlots(
                        LocalDate.now(),
                        LocalTime.of(8, 30),
                        proveedor
                );

        repository.saveAll(List.of(slot1, slot2));

        HorariosPorFechaRequest dto =
                new HorariosPorFechaRequest();

        dto.setFecha(LocalDate.now());

        dto.setIdProveedor(proveedor.getId());

        dto.setDuracionServ(60);

        List<HorasDispDto> horarios =
                service.getHorariosPorFecha(dto);

        assertFalse(horarios.isEmpty());

        HorasDispDto horario = horarios.get(0);

        assertEquals(
                LocalTime.of(8, 0),
                horario.getHoraInicio()
        );

        assertEquals(
                LocalTime.of(9, 0),
                horario.getHoraFin()
        );

        assertNotNull(horario.getIdsSlots());
    }

    @Test
    void actualizarEstados() {

        HorariosSlots slot1 =
                repository.save(
                        new HorariosSlots(
                                LocalDate.now(),
                                LocalTime.of(8, 0),
                                proveedor
                        )
                );

        HorariosSlots slot2 =
                repository.save(
                        new HorariosSlots(
                                LocalDate.now(),
                                LocalTime.of(8, 30),
                                proveedor
                        )
                );

        ActualizarEstadosSlotsRequest dto =
                new ActualizarEstadosSlotsRequest();

        dto.setIdsSlots(
                List.of(
                        slot1.getId(),
                        slot2.getId()
                )
        );

        dto.setEstado("No disponible");

        List<HorariosSlots> actualizados =
                service.actualizarEstados(dto);

        assertEquals(2, actualizados.size());

        actualizados.forEach(slot -> {
            assertEquals(
                    "No disponible",
                    slot.getEstado()
            );
        });
    }

    @Test
    void tieneHorarios_false() {

        boolean resultado =
                service.tieneHorarios(proveedor.getId());

        assertFalse(resultado);
    }

    @Test
    void tieneHorarios_true() {

        repository.save(slot);

        boolean resultado =
                service.tieneHorarios(proveedor.getId());

        assertTrue(resultado);
    }

    @Test
    void reservarSlots() {

        HorariosSlots slot1 =
                repository.save(
                        new HorariosSlots(
                                LocalDate.now(),
                                LocalTime.of(8, 0),
                                proveedor
                        )
                );

        HorariosSlots slot2 =
                repository.save(
                        new HorariosSlots(
                                LocalDate.now(),
                                LocalTime.of(8, 30),
                                proveedor
                        )
                );

        ReservasSlotsRequest dto =
                new ReservasSlotsRequest();

        dto.setIdsSlots(
                List.of(
                        slot1.getId(),
                        slot2.getId()
                )
        );

        dto.setCodigoReserva("RES-001");

        List<HorariosSlots> reservados =
                service.reservarSlots(dto);

        assertEquals(2, reservados.size());

        reservados.forEach(slot -> {

            assertEquals(
                    "Reservado",
                    slot.getEstado()
            );

            assertEquals(
                    "RES-001",
                    slot.getCodigoReserva()
            );
        });
    }

    @Test
    void liberarSlotsReserva() {

        HorariosSlots slot1 =
                repository.save(
                        new HorariosSlots(
                                LocalDate.now(),
                                LocalTime.of(8, 0),
                                proveedor
                        )
                );

        slot1.setEstado("Reservado");
        slot1.setCodigoReserva("RES-001");

        repository.save(slot1);

        service.liberarSlotsReserva("RES-001");

        HorariosSlots actualizado =
                repository.findById(slot1.getId()).orElse(null);

        assertNotNull(actualizado);

        assertEquals(
                "Disponible",
                actualizado.getEstado()
        );

        assertNull(
                actualizado.getCodigoReserva()
        );
    }

    @Test
    void generarSlots() {

        List<HorariosSlots> slots =
                service.generarSlots(
                        List.of(
                                DayOfWeek.MONDAY.getValue()
                        ),
                        LocalDate.now(),
                        LocalDate.now().plusWeeks(1),
                        LocalTime.of(8, 0),
                        LocalTime.of(9, 0),
                        proveedor
                );

        assertNotNull(slots);

        assertFalse(slots.isEmpty());

        HorariosSlots slotGenerado = slots.get(0);

        assertEquals(
                "Disponible",
                slotGenerado.getEstado()
        );

        assertEquals(
                proveedor.getId(),
                slotGenerado.getProveedor().getId()
        );
    }
}
