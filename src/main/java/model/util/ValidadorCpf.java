package model.util;

import java.util.ArrayList;
import java.util.List;

public class ValidadorCpf {

	/* String no Java começa no indice 0 logo a posição 10 é o indice 9 e 
	 a posicao 11 é o indice 10*/
	private static final int PESO_VALIDA_PRIMEIRO_DIGITO = 10;
	private static final int PESO_VALIDA_SEGUNDO_DIGITO = 11;
	
	public static boolean validarCpf(String cpf) {
		boolean cpfValido = false;
		
		boolean calculoValido = false;
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
			calculoValido = true;
		}
		if(cpfInvalidos(cpfValidador)) {
			calculoValido = true;
		}
		return cpfValido;
	}
	
	private static boolean cpfInvalidos(String cpf) {
		boolean retorno = false;
		List<String> cpfs = new ArrayList<String>();
		cpfs.add("11111111111");
		cpfs.add("22222222222");
		cpfs.add("33333333333");
		cpfs.add("44444444444");
		cpfs.add("55555555555");
		cpfs.add("66666666666");
		cpfs.add("77777777777");
		cpfs.add("88888888888");
		cpfs.add("99999999999");
		if(cpfs.contains(cpf)) {
			retorno = true;
		}
		return retorno;
	}
}
