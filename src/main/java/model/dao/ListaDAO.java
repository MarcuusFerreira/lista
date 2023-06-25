package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.exception.ErroCadastroException;
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
			pstmtLista.setString(2, lista.getNomeLista());
			pstmtLista.setObject(3, lista.getDataLista());
			pstmtLista.execute();
			ResultSet resultado = pstmtLista.getGeneratedKeys();
			for (ProdutosLista produto : lista.getProdutosListas()) {
				pstmtListaProduto.setInt(1, lista.getIdLista());
				pstmtListaProduto.setInt(2, produto.getIdProduto());
				pstmtListaProduto.setInt(3, produto.getMarcado());
				pstmtListaProduto.setObject(4, produto.getUnidadeMedida());
				pstmtListaProduto.setDouble(4, produto.getValorMedida());
				pstmtListaProduto.setString(6, produto.getObs());			
			}
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
}
