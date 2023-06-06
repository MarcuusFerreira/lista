package model.bo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.dao.ClienteDAO;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;

public class ClienteBO {
	
	private ClienteDAO dao;
	/* String no Java começa no indice 0 logo a posição 10 é o indice 9 e 
	 a posicao 11 é o indice 10*/
	private int POSICAO_PRIMEIRO_DIGITO = 9;
	private int POSICAO_SEGUNDO_DIGITO = 10;
	private int PESO_VALIDAR_CPF = 10;

	public Cliente cadastrarNovoClienteBO(Cliente cliente) throws ErroCadastroException, CpfInvalidoException {
		if(dao.cpfJaExiste(cliente.getCpf())) {
			throw new CpfInvalidoException("CPF já cadastrado!");
		}
		if (!validarCpf(cliente.getCpf())) {
			throw new CpfInvalidoException("CPF é Inválido!");
		}
		return dao.cadastrarNovoClienteDAO(cliente);
	}

	private boolean validarCpf(String cpf) {
		boolean cpfValido = true;
		Set<String> cpfsInvalidos = new HashSet<String>();
		cpfsInvalidos.add("11111111111");
		cpfsInvalidos.add("22222222222");
		cpfsInvalidos.add("33333333333");
		cpfsInvalidos.add("44444444444");
		cpfsInvalidos.add("55555555555");
		cpfsInvalidos.add("66666666666");
		cpfsInvalidos.add("77777777777");
		cpfsInvalidos.add("88888888888");
		cpfsInvalidos.add("99999999999");
		if(cpf.length() != 11 || cpfsInvalidos.contains(cpf)) {
			cpfValido = false;
		} else {
			if(calcularCpf(cpf)) {
				cpfValido = false;
			}
		}
		return cpfValido;
	}

	private boolean calcularCpf(String cpf) {
		boolean calculoValido = true;
		int primeiroDigito = Character.getNumericValue(cpf.charAt(POSICAO_PRIMEIRO_DIGITO));
		int segundoDigito = Character.getNumericValue(cpf.charAt(POSICAO_SEGUNDO_DIGITO));
		int calculoPrimeiroDigitoVerificador = 0;
		int peso = PESO_VALIDAR_CPF;
		for(int i = 0; i < 9; i++) {
			calculoPrimeiroDigitoVerificador += Character.getNumericValue(cpf.charAt(i)) * peso;
			peso --;
		}
		return calculoValido;
	}
}
