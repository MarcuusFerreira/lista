package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.exception.ErroAtualizarException;
import model.exception.ErroCadastroException;
import model.exception.ErroConsultarException;
import model.exception.ErroExcluirException;
import model.exception.ErroLoginException;
import model.util.FormatadorData;
import model.vo.Cliente;

public class ClienteDAO {

	/*Oque falta nessa classe:
	 * Consultar todos clientes*/
	
	public Cliente cadastrarNovoClienteDAO(Cliente cliente) throws ErroCadastroException {
		Connection connection = Banco.getConnection();
		String sql = "INSERT INTO CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
			if (resultado.next()) {
				cliente.setIdCliente(resultado.getInt(1));
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
		String sql = "SELECT CPF FROM CLIENTE WHERE CPF = ?";
		PreparedStatement stmt = Banco.getPreparedStatement(connection, sql);
		try {
			stmt.setString(1, cpf);
			ResultSet resultado = stmt.executeQuery();
			cpfExiste = resultado.next();
		} catch (SQLException e) {
			throw new ErroCadastroException("Erro ao consultar o CPF no banco, favor consulte o administrador" + e);
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(connection);
		}
		return cpfExiste;
	}

	public boolean verificarCredenciaisDAO(Cliente cliente) throws ErroLoginException {
		Connection connection = Banco.getConnection();
		String sql = "SELECT ID_CLIENTE, NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO FROM CLIENTE WHERE NOME_USUARIO = ? AND SENHA = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		ResultSet resultado = null;
		boolean existe = false;
		try {
			pstmt.setString(1, cliente.getNomeUsuario());
			pstmt.setString(2, cliente.getSenha());
			resultado = pstmt.executeQuery();
			if (resultado.next()) {
				cliente.setIdCliente(resultado.getInt(1));
				cliente.setNomeCliente(resultado.getString(2));
				cliente.setCpf(resultado.getString(3));
				cliente.setDataNascimento(FormatadorData.formatarDataMySQL(resultado.getString(4)));
				cliente.setDataCadastro(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(5)));
				cliente.setTipoUsuario(resultado.getInt(6));
				existe = true;
			}
		} catch (SQLException mensagem) {
			throw new ErroLoginException("erro no metodo verificarCredenciaisDAO, Erro ao verificar as credenciais, favor contate o administrador");
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return existe;
	}
	
	public boolean atualizarCliente (Cliente cliente) throws ErroAtualizarException {
		boolean retorno = false;
		Connection connection = Banco.getConnection();
		String sql = "UPDATE CLIENTE SET NOME_CLIENTE = ?, CPF = ?, DATA_NASCIMENTO = ?, "
				+ "NOME_USUARIO = ?, SENHA = ? WHERE ID_CLIENTE = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setString(1, cliente.getNomeCliente());
			pstmt.setString(2, cliente.getCpf());
			pstmt.setObject(3, cliente.getDataCadastro());
			pstmt.setString(4, cliente.getNomeUsuario());
			pstmt.setString(5, cliente.getSenha());
			pstmt.setInt(6, cliente.getIdCliente());
			if(pstmt.executeUpdate() == 1) {
				retorno = true;
			}
		} catch (SQLException mensagem) {
			throw new ErroAtualizarException("Erro metodo atualizarCliente, Não foi possivel atualizar o cliente");
		}
		finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return retorno;
	}
	
	public boolean excluirCliente(Cliente cliente) throws ErroExcluirException {
		Connection connection = Banco.getConnection();
		String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		int quantidadeLinhasAfetadas = 0;
		
		try {
			pstmt.setInt(1, cliente.getIdCliente());
			quantidadeLinhasAfetadas = pstmt.executeUpdate(sql);
		} catch (SQLException mensagem) {
			throw new ErroExcluirException("Erro no método excluirCliente, Erro ao Excluir o cliente");
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(connection);
		}
		boolean excluiu = quantidadeLinhasAfetadas > 0;
		return excluiu;
	}
	
	public Cliente listarClientePorId (int idCliente) throws ErroConsultarException {
		Cliente cliente = new Cliente();
		Connection connection = Banco.getConnection();
		String sql = "SELECT ID_CLIENTE, NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, "
				+ "TIPO_USUARIO, NOME_USUARIO, SENHA FROM CLIENTE WHERE ID_CLIENTE = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idCliente);
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no método listarClientePorId, Erro ao consultar o cliente");
		}
		return cliente;
	}
	
	public List<Cliente> listarTodosClientes () {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		String query  = "SELECT ID_CLIENTE, NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, "
				+ "TIPO_USUARIO, NOME_USUARIO, SENHA FROM CLIENTE";
		
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Cliente clienteBuscado = montarCliente(resultado);
				clienteBuscado.setIdCliente(Integer.parseInt(resultado.getString(1)));
				clienteBuscado.setNomeCliente(resultado.getString(2));
				clienteBuscado.setCpf(resultado.getString(3));
				clienteBuscado.setDataNascimento(LocalDate.parse(resultado.getString(4)));
				clienteBuscado.setDataCadastro(LocalDateTime.parse(resultado.getString(5)));
				clienteBuscado.setTipoUsuario(Integer.parseInt(resultado.getString(6)));
				clienteBuscado.setNomeUsuario(resultado.getString(7));
				clienteBuscado.setSenha(resultado.getString(8));
				
				clientes.add(clienteBuscado);
			}
		} catch (SQLException e) {
			
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return clientes;
	}
	
	private Cliente montarCliente(ResultSet resultado) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(resultado.getInt(1));
		cliente.setNomeCliente(resultado.getString(2));
		cliente.setCpf(resultado.getString(3));
		cliente.setDataNascimento(FormatadorData.formatarDataMySQL(resultado.getString(4)));
		cliente.setDataCadastro(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(5)));
		cliente.setTipoUsuario(resultado.getInt(6));
		cliente.setNomeUsuario(resultado.getString(7));
		cliente.setSenha(resultado.getString(8));
		return cliente;
	}
}
