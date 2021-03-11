package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.dbConnection;
import excepciones.ClientNotFound;
import excepciones.DuplicateException;
import modelo.Cliente;

public class ClienteDAO implements IClienteDAO{

    private static Connection connection;

    static {
        try{
            connection = dbConnection.conectar();
        } catch (SQLException e){
            System.out.println("Error al conectar con la base de datos");
        }
	}

    @Override
    public boolean anadeCliente(Cliente c) throws DuplicateException {
        Statement statement;

		try {
			statement = connection.createStatement();
			String query = "insert into cliente (codigo_cliente, nombre_cliente, telefono, fax, linea_direccion1, ciudad) VALUES ("+
						c.getCodigoCliente() + ", "  
						+ "'" + c.getNombreCliente() + "', "
						+ "'" + c.getTelefono() + "', "
						+ "'" + c.getFax() + "', "
						+ "'" + c.getLineaDireccion1() + "', "
						+ "'" + c.getCiudad() + "'"
					    + ")";
			System.out.println("Query ejecutada: " + query);
			int id = statement.executeUpdate(query);
			return id != 0; 
			
		} catch (SQLException e) {
			if(e.getErrorCode() == 1062) { throw new DuplicateException(c); } 
			else { System.out.println("**** DB Error (" + e.getErrorCode() + "): No se ha podido añadir ==> " + e.getMessage());}
		}
        return false;
    }

    @Override
    public Cliente MostrarCliente(int id) throws ClientNotFound {
        Statement statement;
		Cliente c = null;
		
		try {
			statement = connection.createStatement();
			String query = "select * from cliente where codigo_cliente = " + id;
			System.out.println("Query ejecutada: " + query);
			ResultSet resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {
				c = obtenerClienteDeQuery(resultSet);
			} else {
				throw new ClientNotFound();
			}
		} catch (SQLException e) {
			System.out.println("**** DB Error (" + e.getErrorCode() + "): Fallo en la consulta ==> " + e.getMessage());
		}
		
		return c;
    }

    private Cliente obtenerClienteDeQuery(ResultSet resultSet) {
		Cliente c;
		try {
			c = new Cliente(resultSet.getInt("codigo_cliente"));
			c.setNombreCliente(resultSet.getString("nombre_cliente"));
			c.setApellidoContacto(resultSet.getString("apellido_contacto"));
			c.setCiudad(resultSet.getString("ciudad"));
			c.setCodigoPostal(resultSet.getString("codigo_postal"));
			c.setFax(resultSet.getString("fax"));
			c.setLimiteCredito(resultSet.getFloat("limite_credito"));
			c.setLineaDireccion1(resultSet.getString("linea_direccion1"));
			c.setLineaDireccion2(resultSet.getString("linea_direccion2"));
			c.setNombreContacto(resultSet.getString("nombre_contacto"));
			c.setPais(resultSet.getString("pais"));
			c.setRegion(resultSet.getString("region"));
			c.setTelefono(resultSet.getString("telefono"));			
		} catch (SQLException e) {
			c = null;
		}
		return c;
	}

    @Override
    public List<Cliente> MostrarTodosClientes() {
        Statement statement;
		List<Cliente> clientes = new ArrayList<>();
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from cliente ORDER BY nombre_cliente ASC");
			Cliente c;
			while (resultSet.next()) {
				c = obtenerClienteDeQuery(resultSet);
				clientes.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return clientes;
    }

    @Override
    public List<Cliente> buscarPorTexto(String cadenaTexto) {
        Statement statement;
		List<Cliente> clientes = new ArrayList<>();
		
		try {
			statement = connection.createStatement();
			String query = "select * from cliente where nombre_cliente like '%" + cadenaTexto + "%' OR apellido_contacto like '%" + cadenaTexto + "%' OR nombre_contacto like '%" + cadenaTexto + "%'" ;
			System.out.println("Query ejecutada: " + query);
			ResultSet resultSet = statement.executeQuery(query);
			
			Cliente c;
			while (resultSet.next()) {
				c = obtenerClienteDeQuery(resultSet);
				clientes.add(c);
			}
		} catch (SQLException e) {
			System.out.println("**** DB Error (" + e.getErrorCode() + "): Fallo en la consulta ==> " + e.getMessage());
		}
		
		return clientes;
    }

    @Override
    public void actualizar(Cliente cliente, String[] params) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void eliminar(Cliente cliente) {
         // TODO Auto-generated method stub
    }


    public void cerrarConexion()
	{
		try {
			if (connection != null) {
				connection.close();				
			}
		} catch (SQLException e) {
		}
	}
}
