package model.bo;

import model.dao.ClienteDAO;
import model.entity.Cliente;

public class ClienteBO {
	
	private ClienteDAO dao;

	public Cliente cadastrarNovoClienteBO(Cliente cliente) {
		return dao.cadastrarNovoClienteDAO(cliente);
	}

}
