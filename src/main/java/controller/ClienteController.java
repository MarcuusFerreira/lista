package controller;

import model.bo.ClienteBO;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;

public class ClienteController {
	
	private ClienteBO bo;
	
	public Cliente cadastrarNovoClienteController (Cliente cliente) throws ErroCadastroException, CpfInvalidoException {
		return bo.cadastrarNovoClienteBO(cliente);
	}
}
