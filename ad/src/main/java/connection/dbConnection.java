package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    public static Connection conectar() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jardineria?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin", "4DM1n4DM1n");
        /*
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("select * from empleado");
       		
        		while (resultSet.next()) {
        			String codigoEmpleado = resultSet.getString("codigo_empleado");
        			String nombre = resultSet.getString("nombre");
        			System.out.println("Resultado X: Codigo empleado: " + codigoEmpleado + " Nombre del empleado: " + nombre);
        		}
        		connection.close();
        */
                return connection;
                
            } 
            
}
