package controller;

import model.bo.ClienteBO;
import model.entity.Cliente;

public class ClienteController {
	
	private ClienteBO bo;
	
	public Cliente cadastrarNovoClienteController (Cliente cliente) {
		return bo.cadastrarNovoClienteBO(cliente);
	}
}
