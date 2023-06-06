package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;
import model.exception.ErroLoginException;

public class ClienteDAO {

	public Cliente cadastrarNovoClienteDAO(Cliente cliente) throws ErroCadastroException {
		Connection connection = Banco.getConnection();
		String sql = "INSERT INTO CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, "
				+ "DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(connection, sql);
		ResultSet resultado = null;
		try {
			stmt.setString(1, cliente.getNomeCliente());
			stmt.setString(2, cliente.getCpf());
			stmt.setObject(3, cliente.getDataNascimento());
			stmt.setObject(4, cliente.getDataCadastro());
			stmt.setInt(5, cliente.getTipoUsuario());
			stmt.setString(6, cliente.getNomeUsuario());
			stmt.setString(7, cliente.getSenha());
			stmt.execute();

			resultado = stmt.getGeneratedKeys();
			if (resultado.next()) {
				cliente.setIdCliente(resultado.getBigDecimal(1));
			}
		} catch (SQLException mensagem) {
			throw new ErroCadastroException("Erro Cadastro de Cliente, por favor contate o administrador");
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(connection);
		}
		return cliente;
	}

	public boolean cpfJaExiste(String cpf) throws ErroCadastroException {
		boolean cpfExiste = false;
		Connection connection = Banco.getConnection();
		String sql = "SELECT CPF FROM CLIENTES WHERE CPF = ?";
		PreparedStatement stmt = Banco.getPreparedStatement(connection, sql);
		ResultSet resultado = null;
		try {
			stmt.setString(1, sql);
			resultado = stmt.executeQuery();
			if (resultado.next()) {
				cpfExiste = true;
			}
		} catch (SQLException e) {
			throw new ErroCadastroException("Erro ao consultar o CPF no banco, favor consulte o administrador");
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(connection);
		}
		return cpfExiste;
	}

	public boolean verificarCredenciais(Cliente cliente) {
		boolean credencialExisteNoSistema = false;
		Connection connection = Banco.getConnection();
		String sql = "SELECT ID_CLIENTE, NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, "
				+ "TIPO_USUARIO, NOME_USUARIO, SENHA FROM CLIENTE WHERE NOME_USUARIO = ? AND SENHA = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		ResultSet resultado = null;
		try {
			pstmt.setString(1, cliente.getNomeUsuario());
			pstmt.setString(2, cliente.getSenha());
			resultado = pstmt.executeQuery();
			if (resultado.next()) {
				cliente.setIdCliente(resultado.getBigDecimal(1));
				cliente.setNomeCliente(resultado.getString(2));
				cliente.setCpf(resultado.getString(3));
				cliente.setDataNascimento(resultado.getDate(4));
				cliente.setDataCadastro(resultado.getDate(5));
				cliente.setTipoUsuario(resultado.getInt(6));
				cliente.setNomeUsuario(resultado.getString(7));
				cliente.setSenha(resultado.getString(8));
				credencialExisteNoSistema = true;
			}
		} catch (SQLException e) {
			throw new ErroLoginException("Erro ao verificar as credenciais, favor contate o administrador");
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}

		return credencialExisteNoSistema;
	}

}
