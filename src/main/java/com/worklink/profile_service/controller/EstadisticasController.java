package com.worklink.profile_service.controller;

import com.worklink.profile_service.DTOS.EstadisticaReservaResponse;
import com.worklink.profile_service.DTOS.ReservaDTO;
import com.worklink.profile_service.services.EstadisticasServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    EstadisticasServices services;

    public EstadisticasController(EstadisticasServices services){
        this.services = services;
    }

    @GetMapping("/reservas/{proveedorId}")
    public ResponseEntity<?> estadisticasDeReservas(@PathVariable Long proveedorId) {
        EstadisticaReservaResponse estadisticas = services.generarEstadisticasReservas(proveedorId);
        if (estadisticas == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(estadisticas);
    }
}
