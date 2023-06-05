package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.Cliente;

public class ClienteDAO {

	public Cliente cadastrarNovoClienteDAO(Cliente cliente) {
		Connection connection = Banco.getConnection();
		String sql = "INSERT INTO CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, " +
		"DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(connection, sql);
		try {
			stmt.setString(1, cliente.getNomeCliente());
			stmt.setString(2, cliente.getCpf());
			stmt.setObject(3, cliente.getDataNascimento());
			stmt.setObject(4, cliente.getDataCadastro());
			stmt.setInt(5, cliente.getTipoUsuario());
			stmt.setString(6, cliente.getNomeUsuario());
			stmt.setString(7, cliente.getSenha());
			stmt.execute();
			
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				cliente.setIdCliente(resultado.getBigDecimal(1));
			}
		} catch (SQLException mensagem) {
			
		}
		return cliente;
	}

}
