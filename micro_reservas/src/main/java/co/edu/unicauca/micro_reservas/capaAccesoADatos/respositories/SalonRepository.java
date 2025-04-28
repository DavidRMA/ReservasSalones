package co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories.conexion.conexionBD;

@Repository
public class SalonRepository {
    private final conexionBD conexionABaseDeDatos;

    public SalonRepository() {
        conexionABaseDeDatos = new conexionBD();
    }

    // Listar todas los salones disponibles para reservas(opcional si esta vacio)
    public Optional<Collection<SalonEntity>> findAll(){
        System.out.println("Listando salones para reservar de la base de datos");
        Collection<SalonEntity> salones = new LinkedList<SalonEntity>();
        conexionABaseDeDatos.conectar();  
        try{
            PreparedStatement sentencia = null;
            String consulta = "SELECT * FROM Salon";
            sentencia = conexionABaseDeDatos.getConexion().prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                SalonEntity objSalon = new SalonEntity();
                objSalon.setId(resultado.getInt("id"));
                objSalon.setNombreSalon(resultado.getString("nombreSalon"));
                objSalon.setUbicacion(resultado.getString("ubicacion"));
                salones.add(objSalon);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch (Exception e){
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return salones.isEmpty() ? Optional.empty() : Optional.of(salones);
    }
}
