package co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Esta clase permite que las Clases tipo Entidad tengan persistencia
 * por medio de una base de datos relacional
 */

public class conexionBD {
    private Connection conexion;

    public conexionBD() {}

    public void conectar(){
        try{
            Class.forName("org.h2.Driver");
            conexion = DriverManager.getConnection("jdbc:h2:mem:testdb","sa", "");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void desconectar(){
        try{
            if(conexion != null) conexion.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
