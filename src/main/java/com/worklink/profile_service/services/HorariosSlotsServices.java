package com.worklink.profile_service.services;

import com.worklink.profile_service.DTOS.horarios.*;
import com.worklink.profile_service.model.HorariosSlots;
import com.worklink.profile_service.model.Proveedor;
import com.worklink.profile_service.repository.HorariosSlotsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HorariosSlotsServices {

    private HorariosSlotsRepository repository;
    private ServicioPerfilServidor servicioProveedor;

    HorariosSlotsServices(HorariosSlotsRepository repository, ServicioPerfilServidor servicioProveedor){
        this.repository = repository;
        this.servicioProveedor = servicioProveedor;
    }

    public HorariosSlots guardarSlot(HorariosSlots slot){
        HorariosSlots save = repository.save(slot);
        if(save.getId() != null) return save;
        return null;
    }

    public List<HorariosSlots> crearSlots(CrearHorariosRequest dto){
        Optional<Proveedor> proveedorOpt = servicioProveedor.obtenerPerfilServidorPorId(dto.getIdProveedor());
        if(proveedorOpt.isEmpty()) return null;
        Proveedor proveedor = proveedorOpt.get();
        List<HorariosSlots> horariosSlots = generarSlots(
                dto.getDiasLaborales(), LocalDate.now(), LocalDate.now().plusMonths(1),
                dto.getHoraInicio(), dto.getHoraFin(), proveedor
        );

        return repository.saveAll(horariosSlots);
    }

    public List<HorariosSlots> añadirSlotsPorRango(AñadirHorarioPorRangoRequest dto){
        Optional<Proveedor> proveedorOpt = servicioProveedor.obtenerPerfilServidorPorId(dto.getIdProveedor());
        if(proveedorOpt.isEmpty()) return null;
        Proveedor proveedor = proveedorOpt.get();
        List<Integer> listaDias = List.of(1, 2, 3, 4, 5, 6, 7);
        List<HorariosSlots> horariosSlots = generarSlots(
                listaDias, dto.getFechaInicio(), dto.getFechaFin(), dto.getHoraInicio(), dto.getHoraFin(), proveedor
        );
        return repository.saveAll(horariosSlots);
    }

    public List<HorariosSlots> getAll(Long idproveedor){
        return repository.obtenerTodos(idproveedor);
    }

    public List<LocalDate> getFechasDisponibles(Long idProveedor){
        List<LocalDate> fechas = repository.obtenerFechas(LocalDate.now(), idProveedor);
        return fechas;
    }

    public List<HorasDispDto> getHorariosPorFecha(HorariosPorFechaRequest dto){
        List<HorariosSlots> slots = repository.obtenerPorFecha(dto.getFecha(), dto.getIdProveedor());
        return horariosPorDuracion(dto, slots);

    }

    @Transactional
    public List<HorariosSlots> actualizarEstados(ActualizarEstadosSlotsRequest dto){
        List<HorariosSlots> allById = repository.findAllById(dto.getIdsSlots());
        allById.forEach(slot -> slot.setEstado(dto.getEstado()));
        return repository.saveAll(allById);
    }

    public boolean tieneHorarios(Long idProveedor){
        Optional<Proveedor> proveedorOpt = servicioProveedor.obtenerPerfilServidorPorId(idProveedor);
        if(proveedorOpt.isEmpty()) return false;
        Proveedor proveedor = proveedorOpt.get();
        return repository.existsByProveedor(proveedor);
    }

    public List<HorariosSlots> generarSlots(List<Integer> listaDias, LocalDate fechaInicio, LocalDate fechaFin, LocalTime horaInicio, LocalTime horaFin, Proveedor proveedor){
        List<DayOfWeek> listDias = listaDias.stream().map(DayOfWeek::of).toList();
        List<HorariosSlots> slots = new ArrayList<>();
        horaFin = horaFin.plusHours(1);
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)){
            if (listDias.contains(fecha.getDayOfWeek())){
                for (LocalTime hora = horaInicio; hora.isBefore(horaFin); hora = hora.plusMinutes(30)){
                    HorariosSlots horariosSlots = new HorariosSlots(fecha, hora, proveedor);
                    slots.add(horariosSlots);
                }
            }
        }
        return slots;
    }


    private List<HorasDispDto> horariosPorDuracion(HorariosPorFechaRequest dto, List<HorariosSlots> slots){
        List<HorasDispDto> lista = new ArrayList<>();
        int incremento = dto.getDuracionServ()/30;
        int endList = slots.size() - incremento;
        for(int i = 0; i <= endList; i += incremento){
            HorasDispDto horario = new HorasDispDto();
            String ids = "";
            int endHorario = i + incremento -1;
            for (int j = i; j <= (endHorario); j++){
                HorariosSlots slot = slots.get(j);
                if(j == i){
                            horario.setHoraInicio(slot.getHoraInicio());
                        }
                if(j != endHorario){
                    if(slot.getHoraFin().equals(slots.get(j+1).getHoraInicio())){                        
                        ids = ids.concat(slot.getId() +",");
                    }else{
                        i = i - endHorario + j;
                        break;
                    }
                }else{
                    ids = ids.concat(slot.getId().toString());
                    horario.setHoraFin(slot.getHoraFin());
                    horario.setIdsSlots(ids);
                    horario.setCodigoReserva(slots.get(i).getCodigoReserva());
                    //aca añadir a la lista
                    lista.add(horario);
                }
            }
        }
        return lista;
    }

    @Transactional
    public List<HorariosSlots> reservarSlots(ReservasSlotsRequest dto) {
        List<HorariosSlots> slots = repository.findAllById(dto.getIdsSlots());
        slots.forEach(slot -> {
            slot.setEstado("Reservado");
            slot.setCodigoReserva(dto.getCodigoReserva());
        });
        return repository.saveAll(slots);
    }

    @Transactional
    public void liberarSlotsReserva(String codigoReserva) {
       List<HorariosSlots> slots = repository.findByCodigoReserva(codigoReserva);
       if(slots.isEmpty()) return;
       slots.forEach(slot -> {
           slot.setEstado("Disponible");
           slot.setCodigoReserva(null);
       });
       repository.saveAll(slots);
    }

}
