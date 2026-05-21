package com.worklink.profile_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.worklink.profile_service.DTOS.EstadisticaReservaResponse;
import com.worklink.profile_service.DTOS.ReservaDTO;
import com.worklink.profile_service.services.EstadisticasServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

class EstadisticasServicesTest {

    private EstadisticasServices servicio;

    @BeforeEach
    void setUp() {

        WebClient.Builder builder = mock(WebClient.Builder.class);
        WebClient webClient = mock(WebClient.class);

        when(builder.baseUrl(anyString()))
                .thenReturn(builder);

        when(builder.build())
                .thenReturn(webClient);

        servicio = Mockito.spy(
                new EstadisticasServices(builder)
        );
    }

    @Test
    void generarEstadisticasReservas_correctamente() {

        ReservaDTO reserva1 = new ReservaDTO();
        reserva1.setTituloServicio("Corte");
        reserva1.setEstadoReserva("COMPLETADA");
        reserva1.setPrecio(new BigDecimal("100"));
        reserva1.setFechaReserva(LocalDate.now());

        ReservaDTO reserva2 = new ReservaDTO();
        reserva2.setTituloServicio("Peinado");
        reserva2.setEstadoReserva("PENDIENTE");
        reserva2.setPrecio(new BigDecimal("50"));
        reserva2.setFechaReserva(LocalDate.now());

        ReservaDTO reserva3 = new ReservaDTO();
        reserva3.setTituloServicio("Corte");
        reserva3.setEstadoReserva("COMPLETADA");
        reserva3.setPrecio(new BigDecimal("150"));
        reserva3.setFechaReserva(LocalDate.now());

        List<ReservaDTO> reservas =
                List.of(reserva1, reserva2, reserva3);

        doReturn(reservas)
                .when(servicio)
                .consultarReservas(1L);

        EstadisticaReservaResponse resultado =
                servicio.generarEstadisticasReservas(1L);

        assertNotNull(resultado);

        assertEquals(3, resultado.getTotalReservas());

        assertEquals(
                250.0,
                resultado.getTotalDineroGenerado()
        );

        assertEquals(
                66.66666666666667,
                resultado.getPorcentajePorServicio().get("Corte")
        );

        assertEquals(
                33.333333333333336,
                resultado.getPorcentajePorServicio().get("Peinado")
        );

        assertEquals(
                66.66666666666667,
                resultado.getPorcentajePorEstado().get("COMPLETADA")
        );

        assertEquals(
                33.333333333333336,
                resultado.getPorcentajePorEstado().get("PENDIENTE")
        );
    }

    @Test
    void generarEstadisticasReservas_listaVacia() {

        doReturn(List.of())
                .when(servicio)
                .consultarReservas(1L);

        EstadisticaReservaResponse resultado =
                servicio.generarEstadisticasReservas(1L);

        assertNull(resultado);
    }

    @Test
    void consultarReservas_mock() {

        ReservaDTO reserva = new ReservaDTO();
        reserva.setTituloServicio("Corte");

        List<ReservaDTO> reservas =
                List.of(reserva);

        doReturn(reservas)
                .when(servicio)
                .consultarReservas(10L);

        List<ReservaDTO> resultado =
                servicio.consultarReservas(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        assertEquals(
                "Corte",
                resultado.get(0).getTituloServicio()
        );
    }
}