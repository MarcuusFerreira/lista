package controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.regex.Pattern;

import model.bo.ClienteBO;
import model.exception.*;
import model.util.ValidadorCpf;
import model.vo.Cliente;

public class ClienteController {

	private static final int IDADE_MINIMA = 12;
	private ClienteBO bo;
	
	private static final int TAMANHO_NOME_USUARIO = 8;
	private static final int TAMANHO_SENHA = 6;
	private static final Pattern CARACTER_MAIUSCULO = Pattern.compile("[A-Z]");
	private static final Pattern CARACTER_MINUSCULO = Pattern.compile("[a-z]");
	private static final Pattern NUMEROS = Pattern.compile("\\d");
	private static final Pattern CARACTER_ESPECIAL = Pattern.compile("[!@#$%^&*()_+=\\\\-\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>\\\\/?]");
	
	public boolean cadastrarNovoClienteController (Cliente cliente) throws ErroCadastroException, CpfInvalidoException, DataNascimentoInvalidaException {
		bo = new ClienteBO();
		if(validarCamposEmBranco(cliente)) {
			throw new ErroCadastroException("Existem Campos em Branco");
		} else {
			if(!validarDataNascimento(cliente.getDataNascimento())) {
				throw new DataNascimentoInvalidaException("Data de nascimento invalida");
			}
			if(validarNomeUsuario(cliente.getNomeUsuario())) {
				throw new ErroCadastroException("Nome de Usuario invalido");
			}
			if(validarSenha(cliente.getSenha())) {
				throw new ErroCadastroException("Senha Inválida!");
			}
		}
		cliente.setNomeCliente(cliente.getNomeCliente().trim());
		return bo.cadastrarNovoClienteBO(cliente);
	}



	public void exportarDadosController(ArrayList<Cliente> clientes, String caminhoEscolhido) {
		bo = new ClienteBO();
		bo.exportarDadosBO(clientes, caminhoEscolhido);
	}

	private boolean validarCamposEmBranco(Cliente cliente) {
		return cliente.getNomeCliente().isBlank()
				|| cliente.getCpf().isBlank()
				|| cliente.getDataNascimento() == null 
				|| cliente.getNomeUsuario().isBlank()
				|| cliente.getSenha().isBlank();		
	}

	public boolean verificarCredenciaisController(Cliente cliente) throws ErroLoginException {
		if(cliente.getNomeUsuario().isBlank() ||
				cliente.getSenha().isBlank()) {
			throw new ErroLoginException("Usuário ou Senha inválidos");
		}
		bo = new ClienteBO();
		return bo.verificarCredenciaisBO(cliente);
	}

	private boolean validarDataNascimento(LocalDate dataNascimento) {
		Period periodo = Period.between(dataNascimento, LocalDate.now());
		int idade = periodo.getYears();
		return idade > IDADE_MINIMA;
	}

	private boolean validarNomeUsuario(String nomeUsuario) {
		return nomeUsuario.length() < TAMANHO_NOME_USUARIO || nomeUsuario.contains(" ") ||
				!CARACTER_MAIUSCULO.matcher(nomeUsuario).find() ||
				!CARACTER_MINUSCULO.matcher(nomeUsuario).find() || 
				CARACTER_ESPECIAL.matcher(nomeUsuario).find() ||
				NUMEROS.matcher(nomeUsuario).find();
	}
	
	private boolean validarSenha(String senha) {
		return senha.length() < TAMANHO_SENHA || senha.contains(" ") ||
				!CARACTER_ESPECIAL.matcher(senha).find() || 
				!CARACTER_MAIUSCULO.matcher(senha).find() || 
				!CARACTER_MINUSCULO.matcher(senha).find() ||
				!NUMEROS.matcher(senha).find();
	}
	
	public ArrayList<Cliente> listarTodosClientes() throws ErroConsultarException {
		bo = new ClienteBO();
		return bo.listarTodosClientes();
	}

	public Cliente listarPorId(int idCliente) throws ErroConsultarException{
		bo = new ClienteBO();
		return bo.listarClientePorId(idCliente);
	}

	public boolean atualizarCliente(Cliente cliente) throws ErroAtualizarException, ErroConsultarException, ErroClienteNaoCadastradoException, ErroCadastroException, CpfInvalidoException {
		bo = new ClienteBO();
		if(validarCamposEmBranco(cliente)) {
			throw new ErroCadastroException("Existem Campos em Branco");
		} else {
			if(validarNomeUsuario(cliente.getNomeUsuario())) {
				throw new ErroCadastroException("Nome de Usuario invalido");
			}
			if(validarSenha(cliente.getSenha())) {
				throw new ErroCadastroException("Senha Inválida!");
			}
		}
		cliente.setNomeCliente(cliente.getNomeCliente().trim());
		return bo.atualizarCliente(cliente);
	}

	public boolean excluirCliente(Cliente cliente) throws ErroExcluirException, ErroConsultarException {
		bo = new ClienteBO();
		return bo.excluirCliente(cliente);
	}
}
