package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.bo.ClienteBO;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;
import model.exception.ErroLoginException;

public class ClienteController {
	
	private ClienteBO bo;
	
	public Cliente cadastrarNovoClienteController (Cliente cliente) throws ErroCadastroException, CpfInvalidoException {
		bo = new ClienteBO();
		if(validarCamposController(cliente)) {
			throw new ErroCadastroException("Existem Campos em Branco");
		}
		return bo.cadastrarNovoClienteBO(cliente);
	}

	public void exportarDadosController(ArrayList<Cliente> clientes, String caminhoEscolhido) {
		bo = new ClienteBO();
		bo.exportarDadosBO(clientes, caminhoEscolhido);
		
	}

	private boolean validarCamposController(Cliente cliente) {
		boolean retorno = false;
		if (cliente.getNomeCliente().trim().isBlank() || cliente.getCpf().trim().isBlank()
				|| dataNascimentoIsNull(cliente.getDataNascimento()) || cliente.getNomeUsuario().trim().isBlank()
				|| cliente.getSenha().trim().isBlank()) {
			retorno = true;
		}
		return retorno;		
	}
	
	private boolean dataNascimentoIsNull(LocalDate dataNascimento) {
		boolean isNull = false;
		if(dataNascimento == null) {
			isNull = true;
		}
		return isNull;
	}

	public Cliente verificarCredenciaisController(Cliente cliente) throws ErroLoginException {
		if(cliente.getNomeUsuario().trim().isBlank() ||
				cliente.getSenha().trim().isBlank()) {
			throw new ErroLoginException("Usuário ou Senha inválidos");
		}
		bo = new ClienteBO();
		return bo.verificarCredenciaisBO(cliente);
	}
}
