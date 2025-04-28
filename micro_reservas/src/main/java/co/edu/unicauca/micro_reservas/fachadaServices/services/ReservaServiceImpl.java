package co.edu.unicauca.micro_reservas.fachadaServices.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import co.edu.unicauca.micro_reservas.MicroReservasApplication;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.models.ReservaEntity;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories.ReservaRepositoryBaseDeDatos;
import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTOPeticion;
import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTORespuesta;

@Service
public class ReservaServiceImpl implements IReservaService{
    private ReservaRepositoryBaseDeDatos servicioAccesoBaseDatos;
    private ModelMapper modelMapper;

    public ReservaServiceImpl(ReservaRepositoryBaseDeDatos servicioAccesoBaseDatos, ModelMapper modelMapper, MicroReservasApplication microReservasApplication) {
        this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReservaDTORespuesta> findAll(){
        List<ReservaDTORespuesta> listaReservas;
        Optional<Collection<ReservaEntity>> reservasEntityOpt = this.servicioAccesoBaseDatos.findAll();
        /* Verifica si el optional esta vacío, si se confirma lo anterior se devuelve una lista vacía */
        if(reservasEntityOpt.isEmpty()){
            listaReservas = List.of();
        }else{
            /* Si el optional no está vacío, se obtiene el valor y se realiza el mapeo */
            Collection<ReservaEntity> reservasEntity = reservasEntityOpt.get();
            listaReservas = this.modelMapper.map(reservasEntity, new TypeToken<List<ReservaDTORespuesta>>() {}.getType());
        }
        return listaReservas;
    }

    @Override
    public ReservaDTORespuesta findById(Integer id){
        ReservaDTORespuesta reservaRetornar = null;
        Optional<ReservaEntity> reservaEntityOpt = this.servicioAccesoBaseDatos.findById(id);
        if(reservaEntityOpt.isPresent()){
            ReservaEntity reservaEntity = reservaEntityOpt.get();
            reservaRetornar = this.modelMapper.map(reservaEntity, ReservaDTORespuesta.class);
        }
        return reservaRetornar;
    }

    @Override
    public ReservaDTORespuesta save(ReservaDTOPeticion reserva){
        ReservaEntity reservaEntity = this.modelMapper.map(reserva, ReservaEntity.class);
        //reservaEntity.setEstadoReserva("Pendiente");
        ReservaEntity objReservaEntity = this.servicioAccesoBaseDatos.save(reservaEntity);
        System.out.println(objReservaEntity);
        ReservaDTORespuesta reservaDTO = this.modelMapper.map(objReservaEntity, ReservaDTORespuesta.class);
        return reservaDTO;
    }

    @Override
    public ReservaDTORespuesta update(Integer id, ReservaDTOPeticion reserva){
        ReservaEntity reservaActualizado = null;
        Optional<ReservaEntity> reservaEntityOpt = this.servicioAccesoBaseDatos.findById(id);
        if(reservaEntityOpt.isPresent()){
            ReservaEntity objReservaNueva = reservaEntityOpt.get();
            objReservaNueva.setNombres(reserva.getNombres());
            objReservaNueva.setApellidos(reserva.getApellidos());
            objReservaNueva.setCantidadPersonas(reserva.getCantidadPersonas());
            objReservaNueva.setFechaReserva(reserva.getFechaReserva());
            objReservaNueva.setHoraInicio(reserva.getHoraInicio());
            objReservaNueva.setHoraFin(reserva.getHoraFin());
            objReservaNueva.setEstadoReserva(reserva.getEstadoReserva());
            objReservaNueva.getObjSalon().setId(reserva.getObjSalon().getId());
            Optional<ReservaEntity> reservaOp = this.servicioAccesoBaseDatos.update(id, objReservaNueva);
            reservaActualizado = reservaOp.get();
        }
        return this.modelMapper.map(reservaActualizado, ReservaDTORespuesta.class);
    }

    @Override
    public Boolean delete(Integer id){
        return this.servicioAccesoBaseDatos.delete(id);
    }
}
