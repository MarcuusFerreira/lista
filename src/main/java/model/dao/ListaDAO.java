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
import model.exception.ErroExcluirException;
import model.util.FormatadorData;
import model.vo.Lista;
import model.vo.ProdutoLista;
import model.vo.UnidadeMedida;

public class ListaDAO {

	public boolean cadastrarLista(Lista lista) throws ErroCadastroException {
		System.out.println("passei no cadastrarLista");
		boolean cadastrado = false;
		Connection connection = Banco.getConnection();
		String insertLista = "INSERT INTO LISTA (ID_CLIENTE, NOME, DATA_LISTA) VALUES (?, ?, ?)";
		PreparedStatement pstmtLista = Banco.getPreparedStatementWithPk(connection, insertLista);
		try {
			pstmtLista.setInt(1, lista.getIdCliente());
			pstmtLista.setString(2, lista.getNomeLista());
			pstmtLista.setObject(3, lista.getDataLista());
			pstmtLista.execute();
			ResultSet resultado = pstmtLista.getGeneratedKeys();
			if(resultado.next()) {
			lista.setIdLista(resultado.getInt(1));
			}
			for (ProdutoLista itemDaListaDeProdutos : lista.getProdutosListas()) {
				inserirProdutoNaLista(itemDaListaDeProdutos, lista.getIdLista());
			}
			cadastrado = true;
		} catch (SQLException e) {
			throw new ErroCadastroException("Erro método cadastrarLista, Erro ao cadastrar a lista. " + "\n Causa: " + e.getCause());
		} finally {
			Banco.closePreparedStatement(pstmtLista);
			Banco.closeConnection(connection);
		}
		return cadastrado;
	}

	private boolean inserirProdutoNaLista(ProdutoLista produto, int idLista) throws ErroCadastroException {
		boolean cadastrado = false;
		Connection connection = Banco.getConnection();
		String insertListaProdutos = " INSERT INTO LISTA_PRODUTO " + " ID_LISTA, ID_PRODUTO, MARCADO, "
				+ " UNIDADE_MEDIDA, VALOR_MEDIDA, OBS " + " VALUES (?, ?, ?, ?, ?, ?) ";
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
			while (resultado.next()) {
				Lista lista = new Lista();
				lista.setIdLista(resultado.getInt(1));
				lista.setNomeLista(resultado.getString(2));
				lista.setDataLista(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(3)));
				listas.add(lista);
			}
			for (Lista lista : listas) {
				lista.setProdutos(buscarProdutosDaLista(lista.getIdLista()));
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no metodo consultarListas, Erro ao consultar as listas");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return listas;
	}

	private List<ProdutoLista> buscarProdutosDaLista(int idLista) throws ErroConsultarException {
		List<ProdutoLista> listas = new ArrayList<ProdutoLista>();
		Connection connection = Banco.getConnection();
		String sql = "SELECT P.ID_PRODUTO, P.NOME, P.SETOR, P.MARCA, P.DATA_CADASTRO, LP.MARCADO, LP.UNIDADE_MEDIDA, LP.VALOR_UNIDADE "
				+ " FROM PRODUTO P INNER JOIN LISTA_PRODUTO LP ON P.ID_PRODUTO = LP.ID_PRODUTO WHERE LP.ID_LISTA = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idLista);
			ResultSet resultado = pstmt.executeQuery();
			while (resultado.next()) {
				ProdutoLista produtoLista = new ProdutoLista();
				produtoLista.setIdProduto(resultado.getInt(1));
				produtoLista.setNome(resultado.getString(2));
				produtoLista.setSetor(resultado.getString(3));
				produtoLista.setMarca(resultado.getString(4));
				produtoLista.setDataCadastro(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(5)));
				produtoLista.setMarcado(resultado.getInt(6));
				produtoLista.setUnidadeMedida(UnidadeMedida.valueOf(resultado.getString(7)));
				produtoLista.setValorMedida(resultado.getInt(8));
				listas.add(produtoLista);
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro np método buscarProdutosDaLista\n" + e.getCause());
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
			if (pstmt.executeUpdate() == 1) {
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
		String sql = "SELECT ID_LISTA FROM LISTA WHERE ID_CLIENTE = ? AND UPPER(NOME) = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idCliente);
			pstmt.setString(2, nome);
			ResultSet resultado = pstmt.executeQuery();
			if (resultado.next()) {
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

	public List<Lista> consultarPorId(int idLista) throws ErroConsultarException {
		List<Lista> listas = new ArrayList<Lista>();
		Connection connection = Banco.getConnection();
		String sqlLista = "SELECT ID_LISTA, ID_CLIENTE, NOME, DATA_LISTA FROM LISTA WHERE ID_LISTA = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sqlLista);
		try {
			pstmt.setInt(1, idLista);
			ResultSet resultado = pstmt.executeQuery();
			while (resultado.next()) {
				Lista lista = new Lista();
				lista.setIdLista(resultado.getInt(1));
				lista.setIdCliente(resultado.getInt(2));
				lista.setNomeLista(resultado.getString(3));
				lista.setDataLista(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(5)));
				listas.add(lista);
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no método, consultarPorId, Erro ao consultar as listas");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return listas;
	}

	public boolean excluirLista(int idLista) throws ErroExcluirException {
		boolean excluiu = false;
		excluirProdutosDaLista(idLista);
		Connection connection = Banco.getConnection();
		String sql = "DELETE FROM LISTA WHERE ID_LISTA = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idLista);
			pstmt.execute();
			excluiu = true;
		} catch (SQLException e) {
			throw new ErroExcluirException("Erro no método excluirLista\n" + e.getCause());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return excluiu;
	}

	private void excluirProdutosDaLista(int idLista) throws ErroExcluirException {
		Connection connection = Banco.getConnection();
		String sql = "DELETE FROM LISTA_PRODUTO WHERE ID_LISTA = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idLista);
			pstmt.execute();
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new ErroExcluirException("Erro no método excluirProdutosDaLista\n" + exception.getCause());
			
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
	}

	public void excluirTodasListasCliente(int idCliente) throws ErroConsultarException, ErroExcluirException {
		List<Lista> listas = consultarListasDAO(idCliente);
		for (Lista cadaLista : listas) {
			excluirProdutosDaLista(cadaLista.getIdLista());
			excluirLista(cadaLista.getIdLista());
		}
	}

	public boolean atualizarItensDaLista(List<ProdutoLista> itens, int idLista) throws ErroAtualizarException {
		boolean itensAtualizados = false;
		Connection connection = Banco.getConnection();
		String sql = "UPDATE LISTA_PRODUTO SET MARCADO = ? WHERE ID_LISTA = ? AND ID_PRODUTO = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		for (ProdutoLista itensDalista : itens) {
			try {
				pstmt.setInt(1, itensDalista.getMarcado());
				pstmt.setInt(2, idLista);
				pstmt.setInt(3, itensDalista.getIdProduto());
				if (pstmt.executeUpdate() > 0) {
					itensAtualizados = true;
				}
			} catch (SQLException e) {
				throw new ErroAtualizarException("Erro ao atualizar a lista");
			} finally {
				Banco.closePreparedStatement(pstmt);
				Banco.closeConnection(connection);
			}
		}
		return itensAtualizados;
	}
	public ArrayList<String> consultarListasClientePorIDDAO(int idCliente) throws ErroConsultarException {
	    Connection connection = null;
	    PreparedStatement stmt = null;
	    ResultSet resultado = null;
	    ArrayList<String> listasClienteID = new ArrayList<>();

	    try {
	        connection = Banco.getConnection();
	        String sql = "SELECT NOME FROM LISTA WHERE ID_CLIENTE = ?";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(1, idCliente);
	        resultado = stmt.executeQuery();

	        while (resultado.next()) {
	            String nomeLista = resultado.getString("NOME");
	            listasClienteID.add(nomeLista);
	        }
	    } catch (SQLException e) {
	        throw new ErroConsultarException(
	                "Erro no método consultarListasClientePorIDDAO Cliente com ID: " + idCliente);
	    } finally {
	        Banco.closeResultSet(resultado);
	        Banco.closeStatement(stmt);
	        Banco.closeConnection(connection);
	    }
	    return listasClienteID;
	}

}
