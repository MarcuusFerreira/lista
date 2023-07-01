package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.ClienteDAO;
import model.exception.*;
import model.geradores.GeradorPlanilha;
import model.util.ValidadorCpf;
import model.vo.Cliente;

public class ClienteBO {


	private ClienteDAO dao;
	public Object exportarDadosBO;


	public Cliente cadastrarNovoClienteBO(Cliente cliente) throws ErroCadastroException, CpfInvalidoException {
		dao = new ClienteDAO();
		cliente.setNomeCliente(cliente.getNomeCliente().toUpperCase().trim());
		if(dao.cpfJaExiste(cliente.getCpf())) {
			throw new CpfInvalidoException("CPF já cadastrado!");
		}
		if (ValidadorCpf.validarCpf(cliente.getCpf())) {
			throw new CpfInvalidoException("CPF é Inválido!");
		}
		return dao.cadastrarNovoClienteDAO(cliente);
	}

	public void exportarDadosBO(ArrayList<Cliente> clientes, String caminhoEscolhido) {
		GeradorPlanilha geradorPlanilha = new GeradorPlanilha();
	}

	public boolean verificarCredenciaisBO(Cliente cliente) throws ErroLoginException {
		dao = new ClienteDAO();
		return dao.verificarCredenciaisDAO(cliente);
	}
	
	public ArrayList<Cliente> listarTodosClientes () throws ErroConsultarException {
		 dao = new ClienteDAO();
		return dao.listarTodosClientes();
	}

	public Cliente listarClientePorId (int idCliente) throws ErroConsultarException {
		dao = new ClienteDAO();
		return dao.listarClientePorId(idCliente);
	}

	public boolean atualizarCliente(Cliente cliente) throws ErroAtualizarException, ErroConsultarException, ErroClienteNaoCadastradoException {
		dao = new ClienteDAO();
		if(dao.clienteExiste(cliente.getIdCliente())) {
			throw new ErroClienteNaoCadastradoException("Cliente não cadastrado");
		}
		return dao.atualizarCliente(cliente);
	}
}
