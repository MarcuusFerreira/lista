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
import model.seletor.ClienteSeletor;
import model.util.FormatadorData;
import model.vo.Cliente;

public class ClienteDAO {
	
	public boolean cadastrarNovoClienteDAO(Cliente cliente) throws ErroCadastroException {
		boolean cadastrou = false;
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
			cadastrou = true;
		} catch (SQLException mensagem) {
			throw new ErroCadastroException("Erro Cadastro de Cliente, por favor contate o administrador");
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(connection);
		}
		return cadastrou;
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
	
	public boolean excluirCliente(Cliente cliente) throws ErroExcluirException, ErroConsultarException {
		ListaDAO listaDAO = new ListaDAO();
		listaDAO.excluirTodasListasCliente(cliente.getIdCliente());
		Connection connection = Banco.getConnection();
		String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		int quantidadeLinhasAfetadas = 0;
		try {
			pstmt.setInt(1, cliente.getIdCliente());
			quantidadeLinhasAfetadas = pstmt.executeUpdate();
		} catch (SQLException mensagem) {
			throw new ErroExcluirException("Erro no método excluirCliente, Erro ao Excluir o cliente");
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return quantidadeLinhasAfetadas > 0;
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
	
	public ArrayList<Cliente> listarTodosClientes () throws ErroConsultarException {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
		String query  = "SELECT ID_CLIENTE, NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, "
				+ "TIPO_USUARIO, NOME_USUARIO, SENHA FROM CLIENTE";
		
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			ResultSet resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Cliente clienteBuscado = montarCliente(resultado);
				clienteBuscado.setIdCliente(Integer.parseInt(resultado.getString(1)));
				clienteBuscado.setNomeCliente(resultado.getString(2));
				clienteBuscado.setCpf(resultado.getString(3));
				clienteBuscado.setDataNascimento(FormatadorData.formatarDataMySQL(resultado.getString(4)));
				clienteBuscado.setDataCadastro(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(5)));
				clienteBuscado.setTipoUsuario(Integer.parseInt(resultado.getString(6)));
				clienteBuscado.setNomeUsuario(resultado.getString(7));
				clienteBuscado.setSenha(resultado.getString(8));
				
				clientes.add(clienteBuscado);
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no método listarTodosClientes\n" + e.getCause());
		} finally {
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

	public boolean clienteExiste(int idCliente) throws ErroConsultarException {
		boolean clienteCadastrado = false;
		Connection connection = Banco.getConnection();
		String sql = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idCliente);
			ResultSet resultado = pstmt.executeQuery();
			if(resultado.next()) {
				clienteCadastrado = true;
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no metodo clienteExiste\n" + e.getCause());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return clienteCadastrado;
	}

	public int contarTotalRegistrosComFiltros(ClienteSeletor seletor) {
		int total = 0;
		Connection conexao = Banco.getConnection();
		String sql = " select count(*) from DB_LISTA_MINIMO.CLIENTE ";
		
		if(seletor.temFiltro()) {
			sql = preencherFiltros(sql, seletor);
		}
		
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			ResultSet resultado = query.executeQuery();
			
			if(resultado.next()) {
				total = resultado.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("Erro contar o total de clientes" 
					+ "\n Causa:" + e.getMessage());
		}finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		
		return total;
	}

	private String preencherFiltros(String sql, ClienteSeletor seletor) {
		
		boolean primeiro = true;
		if(seletor.getNome() != null && !seletor.getNome().trim().isEmpty()) {
			if(primeiro) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			
			sql += " nome LIKE '%" + seletor.getNome() + "%'";
			primeiro = false;
		}
		
		if(seletor.getCpf() != null && !seletor.getCpf().trim().isEmpty()) {
			if(primeiro) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += " cpf LIKE '%" + seletor.getCpf() + "%'";
			primeiro = false;
		}
		
		if(seletor.getDataNascimentoInicial() != null
			&& seletor.getDataNascimentoFinal() != null) {
			if(primeiro) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += " DT_NASCIMENTO BETWEEN '" 
				+ seletor.getDataNascimentoInicial() + "' " 
				+ " AND '" + seletor.getDataNascimentoFinal() + "' ";
			primeiro = false;
		} else {
			if (seletor.getDataNascimentoInicial() != null) {
				if(primeiro) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				//CLIENTES QUE NASCERAM 'A PARTIR' DA DATA INICIAL
				sql += " DT_NASCIMENTO >= '" + seletor.getDataNascimentoInicial() + "' "; 
				primeiro = false;
			}
			
			if (seletor.getDataNascimentoFinal() != null) {
				if(primeiro) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				//CLIENTES QUE NASCERAM 'ATÉ' A DATA FINAL
				sql += " DT_NASCIMENTO <= '" + seletor.getDataNascimentoFinal() + "' "; 
				primeiro = false;
			}
		}
		
		return sql;
	}

	public List<Cliente> consultarComFiltros(ClienteSeletor seletor) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		Connection conexao = Banco.getConnection();
		String sql = " select * from cliente ";
		
		if(seletor.temFiltro()) {
			sql = preencherFiltros(sql, seletor);
		}
		
		if(seletor.temPaginacao()) {
			sql += " LIMIT "  + seletor.getLimite()
				 + " OFFSET " + seletor.getOffset();  
		}
		
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			ResultSet resultado = query.executeQuery();
			
			while(resultado.next()) {
				Cliente clienteBuscado = montarClienteComResultadoDoBanco(resultado);
				clientes.add(clienteBuscado);
			}
			
		}catch (Exception e) {
			System.out.println("Erro ao buscar todos os clientes. \n Causa:" + e.getMessage());
		}finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		
		return clientes;
	}

	private Cliente montarClienteComResultadoDoBanco(ResultSet resultado) throws SQLException {
		Cliente clienteBuscado = new Cliente();
		clienteBuscado.setIdCliente(resultado.getInt("id"));
		clienteBuscado.setNomeCliente(resultado.getString("nome"));
		clienteBuscado.setCpf(resultado.getString("cpf"));
		clienteBuscado.setDataNascimento(resultado.getDate("dt_nascimento").toLocalDate());
		return clienteBuscado;
	}
}
