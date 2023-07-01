package model.bo;

import java.util.ArrayList;
import java.util.List;

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

	public boolean verificarCredenciaisBO(Cliente cliente) throws ErroLoginException {
		dao = new ClienteDAO();
		return dao.verificarCredenciaisDAO(cliente);
	}
	
	public ArrayList<Cliente> listarTodosClientes (Integer IdCliente) {
		 dao = new ClienteDAO();
		ArrayList<Cliente> clientes = new ArrayList<>();
		return clientes;
		
		
	}
}
