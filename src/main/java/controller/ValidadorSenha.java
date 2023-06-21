package controller;

import java.util.regex.Pattern;

public class ValidadorSenha {

	private static final int TAMANHO_SENHA = 6;
	
	public static boolean validarSenha(String senha) {
		boolean senhaValida = true;
		Pattern caracterEspecial = Pattern.compile("[!@#$%^&*()_+=\\\\-\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>\\\\/?]");
		Pattern caracterMaisculo = Pattern.compile("[A-Z]");
		Pattern caracterMinusculos = Pattern.compile("[a-z]");
		Pattern numeros = Pattern.compile("\\d");

		if(senha.length() < TAMANHO_SENHA || senha.contains(" ") ||
			!caracterEspecial.matcher(senha).find() || 
			!caracterMaisculo.matcher(senha).find() || 
			!caracterMinusculos.matcher(senha).find() ||
			!numeros.matcher(senha).find()) {
			senhaValida = false;
		}
		return senhaValida;
	}

}
