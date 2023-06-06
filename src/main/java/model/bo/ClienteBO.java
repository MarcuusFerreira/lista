package model.bo;

import java.util.HashSet;
import java.util.Set;

import model.dao.ClienteDAO;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;

public class ClienteBO {
	
	private ClienteDAO dao;
	/* String no Java começa no indice 0 logo a posição 10 é o indice 9 e 
	 a posicao 11 é o indice 10*/
	private static final int PESO_VALIDA_PRIMEIRO_DIGITO = 10;
	private static final int PESO_VALIDA_SEGUNDO_DIGITO = 11;

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
			if(!calcularCpf(cpf)) {
				cpfValido = false;
			}
		}
		return cpfValido;
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
}
