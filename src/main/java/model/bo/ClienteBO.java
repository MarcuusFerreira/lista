package model.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.dao.ClienteDAO;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;
import model.geradores.GeradorPlanilha;

public class ClienteBO {
	
	private ClienteDAO dao;
	public Object exportarDadosBO;
	/* String no Java começa no indice 0 logo a posição 10 é o indice 9 e 
	 a posicao 11 é o indice 10*/
	private static final int PESO_VALIDA_PRIMEIRO_DIGITO = 10;
	private static final int PESO_VALIDA_SEGUNDO_DIGITO = 11;

	public Cliente cadastrarNovoClienteBO(Cliente cliente) throws ErroCadastroException, CpfInvalidoException {
		dao = new ClienteDAO();
		if(dao.cpfJaExiste(cliente.getCpf())) {
			throw new CpfInvalidoException("CPF já cadastrado!");
		}
		if (calcularCpf(cliente.getCpf())) {
			throw new CpfInvalidoException("CPF é Inválido!");
		}
		return dao.cadastrarNovoClienteDAO(cliente);
	}

	private boolean calcularCpf(String cpf) {
		boolean calculoValido = true;
		String cpfValidador = cpf.substring(0,9);
		int pesoPrimeiroDigito = PESO_VALIDA_PRIMEIRO_DIGITO;
		int pesoSegundoDigito = PESO_VALIDA_SEGUNDO_DIGITO;
		int calculoPrimeiroDigitoVerificador = 0;
		int calculoSegundoDigitoVerificador = 0;
		
		for(int i = 0; i < 9; i++) {
			calculoPrimeiroDigitoVerificador += Character.getNumericValue(cpf.charAt(i)) * pesoPrimeiroDigito;
			pesoPrimeiroDigito--;
		}
		int primeiroDigitoVerificador = 11 -(calculoPrimeiroDigitoVerificador % 11);
		if(primeiroDigitoVerificador >= 10) {
			primeiroDigitoVerificador = 0;
		}
		
		cpfValidador = cpfValidador + primeiroDigitoVerificador;
		
		for(int i = 0; i < 10; i++) {
			calculoSegundoDigitoVerificador += Character.getNumericValue(cpf.charAt(i)) * pesoSegundoDigito;
			pesoSegundoDigito--;
		}
		int segundoDigitoVerificador = 11 - (calculoSegundoDigitoVerificador % 11);
		if(segundoDigitoVerificador >= 10) {
			segundoDigitoVerificador = 0;
		}
		
		cpfValidador = cpfValidador + segundoDigitoVerificador;
		
		if(!cpfValidador.equals(cpf)) {
			calculoValido = false;
		}
		return calculoValido;
	}

	public void exportarDadosBO(ArrayList<Cliente> clientes, String caminhoEscolhido) {

		GeradorPlanilha geradorPlanilha = new GeradorPlanilha();
		
		
	}
}
