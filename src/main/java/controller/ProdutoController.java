package controller;

import java.util.List;

import model.bo.ProdutoBO;
import model.exception.ErroConsultarException;
import model.vo.Produto;

public class ProdutoController {

	private ProdutoBO bo;
	
	public List<Produto> consultarProdutos () throws ErroConsultarException {
		bo = new ProdutoBO();
		return bo.consultarProdutosBO();
	}
	
}
