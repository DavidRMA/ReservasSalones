package co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.micro_reservas.capaAccesoADatos.models.ReservaEntity;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories.conexion.conexionBD;

@Repository
public class ReservaRepositoryBaseDeDatos {
    private final conexionBD conexionABaseDeDatos;

    public ReservaRepositoryBaseDeDatos() {
        conexionABaseDeDatos = new conexionBD();
    }

    /* Este método guarda una reserva en la base de datos, le asigna el ID que la base genera automáticamente, lo 
    recupera completo (por si la base llena más datos que no enviaste) y lo devuelve.*/
    public ReservaEntity save(ReservaEntity objReserva){
        System.out.println("Registrando Reserva en la base de datos");
        ReservaEntity objReservaGuardada = null;
        int resultado = -1;
        try{
            conexionABaseDeDatos.conectar();
            PreparedStatement sentencia = null;
            String consulta = "INSERT INTO Reserva (nombres, apellidos, cantidadPersonas, fechaReserva, horaInicio, horaFin, estadoReserva, idSalon) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            sentencia = conexionABaseDeDatos.getConexion().prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, objReserva.getNombres());
            sentencia.setString(2, objReserva.getApellidos());
            sentencia.setInt(3, objReserva.getCantidadPersonas());
            sentencia.setDate(4, objReserva.getFechaReserva());
            sentencia.setTime(5, Time.valueOf(objReserva.getHoraInicio()));
            sentencia.setTime(6, Time.valueOf(objReserva.getHoraFin()));
            sentencia.setString(7, "Pendiente");
            sentencia.setInt(8, objReserva.getObjSalon().getId());
            resultado = sentencia.executeUpdate();

            ResultSet generateKeys = sentencia.getGeneratedKeys();
            if(generateKeys.next()){
                int idGenerado = generateKeys.getInt(1);
                objReserva.setId(idGenerado);
                System.out.println("Id generado: " + idGenerado);
                if(resultado == 1){
                    objReservaGuardada = this.findById(idGenerado).get();
                }
            }else{
                System.out.println("No se pudo obtener el id generado");
            }
            generateKeys.close();
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("Error en la insercion: " + e.getMessage());
        }
        return objReservaGuardada;
    }
    
    /*  Método para listar todos las reservas de la base de datos*/ 
    public Optional<Collection<ReservaEntity>> findAll(){
        System.out.println("Listando reservas de la base de datos");
        Collection<ReservaEntity> reservas = new LinkedList<ReservaEntity>();
        conexionABaseDeDatos.conectar();
        try{
            PreparedStatement sentencia = null;
            String consulta = "SELECT * FROM Reserva join Salon on Reserva.idSalon = Salon.id";
            sentencia = conexionABaseDeDatos.getConexion().prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                ReservaEntity objReserva = new ReservaEntity();
                objReserva.setId(resultado.getInt("id"));
                objReserva.setNombres(resultado.getString("nombres"));
                objReserva.setApellidos(resultado.getString("apellidos"));
                objReserva.setCantidadPersonas(resultado.getInt("cantidadPersonas"));
                objReserva.setFechaReserva(resultado.getDate("fechaReserva"));
                objReserva.setHoraInicio(resultado.getTime("horaInicio").toLocalTime());
                objReserva.setHoraFin(resultado.getTime("horaFin").toLocalTime());
                objReserva.setEstadoReserva(resultado.getString("estadoReserva"));
                objReserva.setObjSalon(new SalonEntity(resultado.getInt("idSalon"), resultado.getString("nombreSalon"), resultado.getString("ubicacion")));
                reservas.add(objReserva);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return reservas.isEmpty() ? Optional.empty() : Optional.of(reservas);
    }    
    
    /* Método para listar una reserva específico asociado con un salon de la bd*/
    public Optional<ReservaEntity> findById(Integer idReserva){
        System.out.println("Buscando reserva por id en la base de datos");
        ReservaEntity objReserva = null;
        conexionABaseDeDatos.conectar();
        try{
            PreparedStatement sentencia = null;
            String consulta = "SELECT * FROM Reserva join Salon on Reserva.idSalon = Salon.id WHERE Reserva.id = ?";
            sentencia = conexionABaseDeDatos.getConexion().prepareStatement(consulta);
            sentencia.setInt(1, idReserva);
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                System.out.println("Reserva encontrada!");
                objReserva = new ReservaEntity();
                objReserva.setId(resultado.getInt("id"));
                objReserva.setNombres(resultado.getString("nombres"));
                objReserva.setApellidos(resultado.getString("apellidos"));
                objReserva.setCantidadPersonas(resultado.getInt("cantidadPersonas"));
                objReserva.setFechaReserva(resultado.getDate("fechaReserva"));
                objReserva.setHoraInicio(resultado.getTime("horaInicio").toLocalTime());
                objReserva.setHoraFin(resultado.getTime("horaFin").toLocalTime());
                objReserva.setEstadoReserva(resultado.getString("estadoReserva"));
                objReserva.setObjSalon(new SalonEntity(resultado.getInt("idSalon"), resultado.getString("nombreSalon"), resultado.getString("ubicacion")));
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return objReserva == null ? Optional.empty() : Optional.of(objReserva);
    }

    /* Método para actualizar una reserva especifica */
    public Optional<ReservaEntity> update(Integer idReserva, ReservaEntity objReserva){
        System.out.println("Actualizando reserva en la base de datos");
        ReservaEntity objReservaActualizada = null;
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try{
            PreparedStatement sentencia = null;
            String consulta = "UPDATE Reserva SET Reserva.nombres = ?, Reserva.apellidos = ?, Reserva.cantidadPersonas = ?, Reserva.fechaReserva = ?, Reserva.horaInicio = ?, Reserva.horaFin = ?, Reserva.estadoReserva = ?, Reserva.idSalon = ? WHERE Reserva.id = ?";
            sentencia = conexionABaseDeDatos.getConexion().prepareStatement(consulta);
            sentencia.setString(1, objReserva.getNombres());
            sentencia.setString(2, objReserva.getApellidos());
            sentencia.setInt(3, objReserva.getCantidadPersonas());
            sentencia.setDate(4, objReserva.getFechaReserva());
            sentencia.setTime(5, Time.valueOf(objReserva.getHoraInicio()));
            sentencia.setTime(6, Time.valueOf(objReserva.getHoraFin()));
            sentencia.setString(7, objReserva.getEstadoReserva());
            sentencia.setInt(8, objReserva.getObjSalon().getId());
            sentencia.setInt(9, idReserva);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("Error en la actualizacion: " + e.getMessage());
        }
        if(resultado == 1){
            objReservaActualizada = this.findById(idReserva).get();
        }
        return objReservaActualizada == null ? Optional.empty() : Optional.of(objReservaActualizada);
    }

    /* Método para eliminar una reserva especifica */
    public Boolean delete(Integer idReserva){
        System.out.println("Eliminando reserva en la base de datos");
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try{
            PreparedStatement sentencia = null;
            String consulta = "DELETE FROM Reserva WHERE id = ?";
            sentencia = conexionABaseDeDatos.getConexion().prepareStatement(consulta);
            sentencia.setInt(1, idReserva);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("Error en la eliminacion: " + e.getMessage());
        }
        if(resultado == 1){
            System.out.println("Eliminacion exitosa!");
        }
        return resultado == 1;
    }
}


