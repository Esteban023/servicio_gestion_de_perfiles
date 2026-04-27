package com.worklink.profile_service.controller;

import com.worklink.profile_service.DTOS.horarios.*;
import com.worklink.profile_service.model.HorariosSlots;
import com.worklink.profile_service.services.HorariosSlotsServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/horarios")
public class HorariosSlotsController {

    private HorariosSlotsServices service;

    public HorariosSlotsController(HorariosSlotsServices service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearHorarios(@RequestBody CrearHorariosRequest dto){
        List<HorariosSlots> horariosSlots = service.crearSlots(dto);
        return ResponseEntity.ok(horariosSlots);
    }

    @PostMapping("/addPorRango")
    public ResponseEntity<?> añadirHorarioPorRango(@RequestBody AñadirHorarioPorRangoRequest dto){
        List<HorariosSlots> nuevosHorarios = service.añadirSlotsPorRango(dto);
        return ResponseEntity.ok(nuevosHorarios);
    }

    @GetMapping("getAll/{idProveedor}")
    public ResponseEntity<?> obtenerTodos(@PathVariable Long idProveedor){
        List<HorariosSlots> all = service.getAll(idProveedor);
        return ResponseEntity.ok(all);
    }
    
    @GetMapping("/getFechas/{proveedorId}")
    public ResponseEntity<?> obtenerFechasDisponibles(@PathVariable Long proveedorId){
        List<LocalDate> fechasDisponibles = service.getFechasDisponibles(proveedorId);
        return ResponseEntity.ok(fechasDisponibles);
    }

    @PostMapping("/getPorFecha")
    public ResponseEntity<?> obtenerHorariosPorFecha(@RequestBody HorariosPorFechaRequest dto){
        List<HorasDispDto> horariosPorFecha = service.getHorariosPorFecha(dto);
        return ResponseEntity.ok(horariosPorFecha);
    }

    @GetMapping("/tieneHorarios/{idProveedor}")
    public ResponseEntity<?> verificarSiTieneHorarios(@PathVariable Long idProveedor){
        boolean exists = service.tieneHorarios(idProveedor);
        return ResponseEntity.ok(Map.of("respuesta", exists));
    }

    @PutMapping("/actualizar/estado")
    public ResponseEntity<?> actulizarEstadoSlot(@RequestBody ActualizarEstadosSlotsRequest dto){
        List<HorariosSlots> horariosSlots = service.actualizarEstados(dto);
        return ResponseEntity.ok(horariosSlots);
    }

    @PutMapping("/reservar")
    public ResponseEntity<?> reservarSlots(@RequestBody ReservasSlotsRequest dto) {
        List<HorariosSlots> horariosSlots = service.reservarSlots(dto);        
        return ResponseEntity.ok(horariosSlots);
    }

    @PutMapping("/liberar/{codigoReserva}")
    public ResponseEntity<?> liberarReserva(@PathVariable String codigoReserva) {
        service.liberarSlotsReserva(codigoReserva);
        return ResponseEntity.ok().build();
    }

}
