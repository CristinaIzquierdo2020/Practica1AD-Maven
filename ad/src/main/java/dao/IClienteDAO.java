package dao;

import java.util.List;

import excepciones.ClientNotFound;
import excepciones.DuplicateException;
import modelo.Cliente;

public interface IClienteDAO {

    public boolean anadeCliente(Cliente c) throws DuplicateException;
	public Cliente MostrarCliente(int id) throws ClientNotFound;
	public List<Cliente> MostrarTodosClientes();
	public List<Cliente> buscarPorTexto(String texto);
	public void actualizar(Cliente cliente, String[] params);
	public void eliminar(Cliente cliente);
}

