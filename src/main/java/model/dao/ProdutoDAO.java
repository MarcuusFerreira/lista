package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.exception.ErroAtualizarException;
import model.exception.ErroCadastroException;
import model.exception.ErroConsultarException;
import model.exception.ErroExcluirException;
import model.seletor.ProdutoSeletor;
import model.util.FormatadorData;
import model.vo.Produto;

public class ProdutoDAO {

	public List<Produto> consultarProdutosDAO() throws ErroConsultarException {
		ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
		Connection connection = Banco.getConnection();
		String sql = "SELECT ID_PRODUTO, SETOR, MARCA, NOME, DATA_CADASTRO FROM PRODUTO WHERE DATA_EXCLUSAO IS NULL";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			ResultSet resultado = pstmt.executeQuery();
			while (resultado.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(resultado.getInt(1));
				produto.setSetor(resultado.getString(2));
				produto.setMarca(resultado.getString(3));
				produto.setNome(resultado.getString(4));
				produto.setDataCadastro(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(5)));
				listaProdutos.add(produto);
			}
		} catch (SQLException excecao) {
			throw new ErroConsultarException("Erro no método consultarProdutosDAO, Erro a ocunsultar os produtos");
		}
		return listaProdutos;
	}

	public boolean cadastrarProduto(Produto produto) throws ErroCadastroException {
		boolean cadastrou = false;
		Connection connection = Banco.getConnection();
		String sql = "INSERT INTO PRODUTO (SETOR, MARCA, NOME, DATA_CADASTRO) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(connection, sql);
		try {
			pstmt.setString(1, produto.getSetor());
			pstmt.setString(2, produto.getMarca());
			pstmt.setString(3, produto.getNome());
			pstmt.setObject(4, produto.getDataCadastro());
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				produto.setIdProduto(resultado.getInt(1));
				cadastrou = true;
			}
		} catch (SQLException e) {
			throw new ErroCadastroException("Erro no método cadastrarProduto");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return cadastrou;
	}

	public boolean atualizarProduto(Produto produto) throws ErroAtualizarException {
		boolean atualizou = false;
		Connection connection = Banco.getConnection();
		String sql = "UPDATE PRODUTO SET NOME = ?, MARCA = ?, SETOR = ? WHERE ID_PRODUTO = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setString(1, produto.getNome());
			pstmt.setString(2, produto.getMarca());
			pstmt.setString(3, produto.getSetor());
			int linhasAfetadas = pstmt.executeUpdate();
			if (linhasAfetadas > 0) {
				atualizou = true;
			}
		} catch (SQLException e) {
			throw new ErroAtualizarException("Erro no método atualizarProduto");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return atualizou;
	}

	public boolean excluirProduto(Produto produto) throws ErroExcluirException {
		boolean excluiu = false;
		Connection connection = Banco.getConnection();
		String sql = "UPDATE PRODUTO SET DATA_EXCLUSAO = ? WHERE ID_PRODUTO = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setObject(1, produto.getDataExclusao());
			pstmt.setInt(2, produto.getIdProduto());
			int linhasAfetadas = pstmt.executeUpdate();
			if (linhasAfetadas > 0) {
				excluiu = true;
			}

		} catch (SQLException e) {
			throw new ErroExcluirException("Erro no método excluirProduto");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return excluiu;
	}

	public Produto consultarPorId(int idProduto) throws ErroConsultarException {
		Produto produto = new Produto();
		Connection connection = Banco.getConnection();
		String sql = "SELECT ID_PRODUTO, NOME, SETOR, MARCA, DATA_CADASTRO FROM PRODUTO WHERE DATA_EXCLUSAO IS NULL AND ID_PRODUTO =?";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			pstmt.setInt(1, idProduto);
			ResultSet resultado = pstmt.executeQuery();
			if (resultado.next()) {
				produto.setIdProduto(resultado.getInt(1));
				produto.setNome(resultado.getString(2));
				produto.setSetor(resultado.getString(3));
				produto.setMarca(resultado.getString(4));
				produto.setDataCadastro(FormatadorData.formatarLocalDateTimeMySQL(resultado.getString(5)));
			}
		} catch (SQLException e) {
			throw new ErroConsultarException("Erro no método consultarPorId");
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return produto;
	}

	public List<Produto> consultarComFiltros(ProdutoSeletor produtoSeletor) throws ErroConsultarException {
		List<Produto> produtos = new ArrayList<Produto>();
		Connection connection = Banco.getConnection();
		String sql = "SELECT ID_PRODUTO, SETOR, MARCA, NOME, DATA_CADASTRO FROM PRODUTO WHERE DATA_EXCLUSAO IS NULL";
		
		if(produtoSeletor.temFiltro()) {
			sql = preencherFiltros(sql, produtoSeletor);
		}
		
		if(produtoSeletor.temPaginacao()) {
			sql += " LIMIT " + produtoSeletor.getLimite() +
					" OFFSET " + produtoSeletor.getOffset();
		}
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			ResultSet resultado = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ErroConsultarException("Erro no método consultarComFiltros");
		}finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(connection);
		}
		return produtos;
	}

	private String preencherFiltros(String sql, ProdutoSeletor produtoSeletor) {
		if(produtoSeletor.getNome().isBlank() && produtoSeletor.getNome() != null) {
			sql += " AND NOME LIKE '%" + produtoSeletor.getNome() + "%' ";
		}
		if(produtoSeletor.getMarca().isBlank() && produtoSeletor.getMarca() != null) {
			sql += " AND NOME LIKE '%" + produtoSeletor.getMarca() + "%' ";
		}
		if(produtoSeletor.getSetor().isBlank() && produtoSeletor.getSetor() != null) {
			sql += " AND NOME LIKE '%" + produtoSeletor.getSetor() + "%' ";
		}
		if(produtoSeletor.getDataCadastroInicial() != null) {
			sql += " AND DATA_CADASTRO >= " + produtoSeletor.getDataCadastroInicial();
		}
		if(produtoSeletor.getDataCadastroFim() != null) {
			sql += " AND DATA_CADASTRO <= " + produtoSeletor.getDataCadastroFim();
		}
		return sql;
	}
}
