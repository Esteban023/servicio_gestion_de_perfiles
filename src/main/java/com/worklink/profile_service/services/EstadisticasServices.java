package com.worklink.profile_service.services;

import com.worklink.profile_service.DTOS.EstadisticaReservaResponse;
import com.worklink.profile_service.DTOS.ReservaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;

@Component
public class EstadisticasServices {
    private final WebClient webClient;

    public EstadisticasServices(WebClient.Builder builder,
                                @Value("${services.reservas.url}") String reservasUrl) {
        this.webClient = builder.baseUrl(reservasUrl).build();
    }

    public List<ReservaDTO> consultarReservas(Long idProveedor){
        return webClient.get()
                .uri("misReservasProveedor/{idProveedor}", idProveedor)
                .retrieve()
                .bodyToFlux(ReservaDTO.class)
                .collectList()
                .block();
    }

    public EstadisticaReservaResponse generarEstadisticasReservas(Long proveedorId) {
        List<ReservaDTO> reservas = consultarReservas(proveedorId);
        if (reservas.isEmpty()) return null;
        return procesarDatosReservas(reservas);
    }

    private EstadisticaReservaResponse procesarDatosReservas(List<ReservaDTO> reservas) {
        double totalGenerado = 0.0;
        int totalReservas = reservas.size();
        HashMap<String, Double> porEstado = new HashMap<>();
        HashMap<String, Double> porServicio = new HashMap<>();

        for (ReservaDTO reserva : reservas) {
            if (reserva.getEstadoReserva().equals("COMPLETADA")) {
                totalGenerado += reserva.getPrecio().doubleValue();
            }
            String titleService = reserva.getTituloServicio();
            String estadoReserva = reserva.getEstadoReserva();
            porServicio.compute(titleService, (k, valor) -> valor == null ? 1 : valor + 1);
            porEstado.compute(estadoReserva, (k, valor) -> valor == null ? 1 : valor + 1);
        }

        porEstado.replaceAll((k, valor) -> valor * (100.0 / totalReservas));
        porServicio.replaceAll((k, valor) -> valor * (100.0 / totalReservas));

        return new EstadisticaReservaResponse(porServicio, porEstado, totalReservas, totalGenerado);
    }
}