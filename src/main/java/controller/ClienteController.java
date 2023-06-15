package controller;

import java.util.ArrayList;

import model.bo.ClienteBO;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;

public class ClienteController {
	
	private ClienteBO bo;
	
	public Cliente cadastrarNovoClienteController (Cliente cliente) throws ErroCadastroException, CpfInvalidoException {
		bo = new ClienteBO();
		return bo.cadastrarNovoClienteBO(cliente);
	}

	public void exportarDadosController(ArrayList<Cliente> clientes, String caminhoEscolhido) {
		bo = new ClienteBO();
		bo.exportarDadosBO(clientes, caminhoEscolhido);
		
	}
}
