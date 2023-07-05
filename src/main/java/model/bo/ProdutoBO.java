package model.bo;

import java.util.List;

import model.dao.ProdutoDAO;
import model.exception.ErroConsultarException;
import model.vo.Produto;

public class ProdutoBO {

	private ProdutoDAO dao;
	
	public List<Produto> consultarProdutosBO() throws ErroConsultarException {
		dao = new ProdutoDAO();
		return  dao.consultarProdutosDAO();
	}



}
