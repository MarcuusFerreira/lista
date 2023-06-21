package model.bo;

import java.util.ArrayList;

import model.dao.ClienteDAO;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;
import model.exception.ErroLoginException;
import model.geradores.GeradorPlanilha;
import model.util.ValidadorCpf;
import model.vo.Cliente;

public class ClienteBO {

	private ClienteDAO dao;
	public Object exportarDadosBO;


	public Cliente cadastrarNovoClienteBO(Cliente cliente) throws ErroCadastroException, CpfInvalidoException {
		dao = new ClienteDAO();
		cliente.setNomeCliente(cliente.getNomeCliente().toUpperCase());
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

	public Cliente verificarCredenciaisBO(Cliente cliente) throws ErroLoginException {
		dao = new ClienteDAO();
		return dao.verificarCredenciaisDAO(cliente);
	}
}
