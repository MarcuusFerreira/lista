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
import model.vo.ProdutosLista;

public class ListaDAO {

	public boolean cadastrarLista(Lista lista) throws ErroCadastroException {
		boolean cadastrado = false;
		Connection connection = Banco.getConnection();
		String insertLista = "INSERT INTO LISTA ID_CLIENTE, NOME, DATA_LISTA VALUES (?, ?, ?)";
		PreparedStatement pstmtLista = Banco.getPreparedStatementWithPk(connection, insertLista);
		String insertListaProdutos = "INSERT INTO LISTA_PRODUTO ID_LISTA, ID_PRODUTO, MARCADO " +
		"UNIDADE_MEDIDA, VALOR_MEDIDA, OBS VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmtListaProduto = Banco.getPreparedStatement(connection, insertListaProdutos);
		try {
			pstmtLista.setInt(1, lista.getIdCliente());
			pstmtLista.setString(2, lista.getNomeLista().toUpperCase());
			pstmtLista.setObject(3, lista.getDataLista());
			pstmtLista.execute();
			ResultSet resultado = pstmtLista.getGeneratedKeys();
			lista.getProdutosListas().forEach(produto -> {
				try {
					pstmtListaProduto.setInt(1, lista.getIdLista());
					pstmtListaProduto.setInt(2, produto.getIdProduto());
					pstmtListaProduto.setInt(3, produto.getMarcado());
					pstmtListaProduto.setObject(4, produto.getUnidadeMedida());
					pstmtListaProduto.setDouble(4, produto.getValorMedida());
					pstmtListaProduto.setString(6, produto.getObs());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			lista.setIdLista(resultado.getInt(1));
			cadastrado = true;
		} catch (SQLException e) {
			throw new ErroCadastroException("Erro no metodo cadastrarLista, Erro ao cadastrar a lista");
		} finally {
			Banco.closePreparedStatement(pstmtListaProduto);
			Banco.closePreparedStatement(pstmtLista);
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
}
