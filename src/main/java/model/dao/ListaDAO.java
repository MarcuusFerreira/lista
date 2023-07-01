package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.exception.ErroAtualizarException;
import model.exception.ErroCadastroException;
import model.exception.ErroConsultarException;
import model.util.FormatadorData;
import model.vo.Lista;
import model.vo.ProdutoLista;

public class ListaDAO {

	public boolean cadastrarLista(Lista lista) throws ErroCadastroException {
		boolean cadastrado = false;
		Connection connection = Banco.getConnection();
		String insertLista = "INSERT INTO LISTA ID_CLIENTE, NOME, DATA_LISTA VALUES (?, ?, ?)";
		PreparedStatement pstmtLista = Banco.getPreparedStatementWithPk(connection, insertLista);
		try {
			pstmtLista.setInt(1, lista.getIdCliente());
			pstmtLista.setString(2, lista.getNomeLista().toUpperCase());
			pstmtLista.setObject(3, lista.getDataLista());
			pstmtLista.execute();
			ResultSet resultado = pstmtLista.getGeneratedKeys();
			lista.setIdLista(resultado.getInt(1));
			for(ProdutoLista itemDaListaDeProdutos: lista.getProdutosListas()) {
				inserirProdutoNaLista(itemDaListaDeProdutos, lista.getIdLista());
			}
			cadastrado = true;
		} catch (SQLException e) {
			throw new ErroCadastroException("Erro método cadastrarLista, Erro ao cadastrar a lista. "
					+ "\n Causa: " + e.getCause());
		} finally {
			Banco.closePreparedStatement(pstmtLista);
			Banco.closeConnection(connection);
		}
		return cadastrado;
	}
	
	private boolean inserirProdutoNaLista(ProdutoLista produto, int idLista) throws ErroCadastroException {
		boolean cadastrado = false;
		Connection connection = Banco.getConnection();
		String insertListaProdutos =  " INSERT INTO LISTA_PRODUTO "
								    + " ID_LISTA, ID_PRODUTO, MARCADO, " 
									+ " UNIDADE_MEDIDA, VALOR_MEDIDA, OBS "
									+ " VALUES (?, ?, ?, ?, ?, ?) ";
		PreparedStatement pstmtListaProduto = Banco.getPreparedStatement(connection, insertListaProdutos);
		
		try {
			pstmtListaProduto.setInt(1, idLista);
			pstmtListaProduto.setInt(2, produto.getIdProduto());
			pstmtListaProduto.setInt(3, produto.getMarcado());
			pstmtListaProduto.setObject(4, produto.getUnidadeMedida());
			pstmtListaProduto.setDouble(4, produto.getValorMedida());
			pstmtListaProduto.setString(6, produto.getObs());
			cadastrado = true;
		} catch (SQLException e) {
			throw new ErroCadastroException("Erro no método inserirProdutoNaLista, Erro ao cadastrar a lista");
		} finally {
			Banco.closePreparedStatement(pstmtListaProduto);
			Banco.closeConnection(connection);
		}
		return cadastrado;
	}

	public List<Lista> consultarListasDAO(int idCliente) throws ErroConsultarException {
		List<Lista> listas = new ArrayList<Lista>();
		Connection connection = Banco.getConnection();
		String sql = "select id_lista, nome, data_lista from lista where id_cliente = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		
		try {
			pstmt.setInt(1, idCliente);
			ResultSet resultado = pstmt.executeQuery();
			if (resultado.next()) {
				Lista lista = new Lista();
				lista.setIdLista(resultado.getInt(1));
				lista.setNomeLista(resultado.getString(2));
				lista.setDataLista(FormatadorData.formatarDataMySQL(resultado.getString(3)));
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no metodo consultarListas, Erro ao consultar as listas");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return listas;
	}
	
	public boolean atualizarLista(Lista lista) throws ErroAtualizarException {
		boolean resultado = false;
		Connection connection = Banco.getConnection();
		String sql = "update lista set nome = ? where id_lista = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		
		try {
			pstmt.setString(1, lista.getNomeLista());
			pstmt.setInt(2, lista.getIdLista());
			if(pstmt.executeUpdate() == 1) {
				resultado = true;
			}
		} catch (SQLException e) {
			throw new ErroAtualizarException("Erro no método atualizarLista, Erro ao atualizar a lista");
		}
		return resultado;
	}

	public boolean cadastrouMesmoNome(int idCliente, String nome) {
		boolean retorno = false;
		Connection connection = Banco.getConnection();
		String sql = "select id_lista from lista where id_cliente = ? and nome = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idCliente);
			pstmt.setString(2, nome);
			ResultSet resultado = pstmt.executeQuery();
			if(resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return retorno;
	}

	public List<Lista> consultarListasClientePorIDDAO(int idCliente) throws ErroConsultarException {
		ArrayList<Lista> listasClienteID = new ArrayList<>();
		Connection connection = Banco.getConnection();
		String sql = "SELECT NOME FROM LISTA WHERE ID_CLIENTE = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		ResultSet resultado = null;
		try {
			pstmt.setInt(1, idCliente);
			resultado = pstmt.executeQuery();
			while (resultado.next()) {
				Lista nomesListasClienteID = new Lista();
				nomesListasClienteID.setNomeLista(resultado.getString(1));
                listasClienteID.add(nomesListasClienteID);
			}
		} catch (SQLException e) {
			throw new ErroConsultarException(
					"Erro no método consultarListasClientePorIDDAO Cliente com ID: " + idCliente);
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return listasClienteID;
	}
	
	public Lista consultarPorId(int idLista) throws ErroConsultarException {
		Lista lista = new Lista();
		Connection connection = Banco.getConnection();
		String sqlLista = "SELECT ID_LISTA, ID_CLIENTE, NOME, DATA_LISTA FROM LISTA WHERE ID_LISTA = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sqlLista);
		try {
			ResultSet resultado = pstmt.executeQuery();
			if(resultado.next()) {
				lista.setIdLista(resultado.getInt(1));
				lista.setIdCliente(resultado.getInt(2));
				lista.setNomeLista(resultado.getString(3));
				lista.setDataLista(FormatadorData.formatarDataMySQL(resultado.getString(5)));
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no método, consultarPorId, Erro ao consultar as listas");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return lista;
	}
}
