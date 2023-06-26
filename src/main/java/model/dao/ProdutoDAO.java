package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.exception.ErroConsultarException;
import model.util.FormatadorData;
import model.vo.Produto;

public class ProdutoDAO {

	
	public List<Produto> consultarProdutosDAO() throws ErroConsultarException{
		ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
		Connection connection = Banco.getConnection();
		String sql = "SELECT ID_PRODUTO, SETOR, MARCA, NOME, DATA_CADASTRO FROM PRODUTO";
		PreparedStatement pstmt = Banco.getPreparedStatement(connection, sql);
		try {
			ResultSet resultado = pstmt.executeQuery();
			while(resultado.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(resultado.getInt(1));
				produto.setSetor(resultado.getString(2));
				produto.setMarca(resultado.getString(3));
				produto.setNome(resultado.getString(4));
				produto.setDataCadastro(FormatadorData.formatarDataMySQL(resultado.getString(5)));
				listaProdutos.add(produto);
			}
		} catch (SQLException excecao) {
			throw new ErroConsultarException("Erro no método consultarProdutosDAO, Erro a ocunsultar os produtos");
		}
		return listaProdutos;
	}
}
