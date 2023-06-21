package controller;

import java.util.regex.Pattern;

public class ValidadorNomeUsuario {

	private static final int TAMANHO_NOME_USUARIO = 8;
	
	public static boolean validarNomeUsuario(String nomeUsuario) {
		boolean nomeValido = true;
		Pattern caracterMaiusculo = Pattern.compile("[A-Z]");
		Pattern caracterMinusculo = Pattern.compile("[a-z]");
		Pattern numero = Pattern.compile("\\d");
		Pattern caracterEspecial = Pattern.compile("[!@#$%^&*()_+=\\\\-\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>\\\\/?]");
		if(nomeUsuario.length() < TAMANHO_NOME_USUARIO || nomeUsuario.contains(" ") ||
			!caracterMaiusculo.matcher(nomeUsuario).find() ||
			!caracterMinusculo.matcher(nomeUsuario).find() || 
			caracterEspecial.matcher(nomeUsuario).find() ||
			numero.matcher(nomeUsuario).find()) {
			nomeValido = false;
		}
		return nomeValido;
	}

}
