package com.worklink.profile_service.services;

import com.worklink.profile_service.DTOS.EstadisticaReservaResponse;
import com.worklink.profile_service.DTOS.ReservaDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;

@Component
public class EstadisticasServices {
    private final WebClient webClient;

    public EstadisticasServices(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8083/api/reservas/").build();
    }

    public List<ReservaDTO> consultarReservas(Long idProveedor){
        return webClient.get()
                .uri("misReservasProveedor/{idProveedor}", idProveedor)
                .retrieve()
                .bodyToFlux(ReservaDTO.class)
                .collectList()
                .block();
    }

    public EstadisticaReservaResponse generarEstadisticasReservas(Long proveedorId){
        List<ReservaDTO> reservas = consultarReservas(proveedorId);
        if(reservas.isEmpty()) return null;
        EstadisticaReservaResponse estadistica = procesarDatosReservas(reservas);
        return estadistica;
    }
    private EstadisticaReservaResponse procesarDatosReservas(List<ReservaDTO> reservas){
        int totalReservas = reservas.size();
        double totalGenerado = 0.0;
        EstadisticaReservaResponse estadistica;
        HashMap<String, Double> porServicio = new HashMap<>();
        HashMap<String, Double> porEstado = new HashMap<>();
        for (ReservaDTO reserva : reservas){
            if(reserva.getEstadoReserva().equals("COMPLETADA"));{
                totalGenerado += reserva.getPrecio().doubleValue();
            }
            String titleService = reserva.getTituloServicio();
            String estadoReserva = reserva.getEstadoReserva();
            porServicio.compute(titleService, (k, valor) -> valor == null ? 1 : valor+1);
            porEstado.compute(estadoReserva, (key, value) -> value == null ? 1 : value+1);
        }
        porEstado.replaceAll((key, valor) -> valor*100/totalReservas);
        porServicio.replaceAll((key, valor) -> valor*100/totalReservas);
        estadistica =  new EstadisticaReservaResponse(porServicio, porEstado, totalReservas, totalGenerado);
        return estadistica;
    }
}
