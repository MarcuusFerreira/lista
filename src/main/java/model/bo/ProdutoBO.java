package model.bo;

import java.util.List;

import model.dao.ProdutoDAO;
import model.exception.ErroAtualizarException;
import model.exception.ErroCadastroException;
import model.exception.ErroConsultarException;
import model.exception.ErroExcluirException;
import model.seletor.ProdutoSeletor;
import model.vo.Produto;

public class ProdutoBO {

	private ProdutoDAO dao;
	
	public List<Produto> consultarProdutosBO() throws ErroConsultarException {
		dao = new ProdutoDAO();
		return  dao.consultarProdutosDAO();
	}
	
	public Produto consultarPorId(int IdProduto) throws ErroConsultarException {
		dao = new ProdutoDAO();
		return  dao.consultarPorId(IdProduto);
	}

	public boolean cadastrarProduto(Produto produto) throws ErroCadastroException {
		dao = new ProdutoDAO();
		produto.setNome(produto.getNome().toUpperCase());
		produto.setMarca(produto.getMarca().toUpperCase());
		produto.setSetor(produto.getSetor().toUpperCase());
		return  dao.cadastrarProduto(produto);
	}
	
	public boolean atualizarProduto(Produto produto) throws ErroAtualizarException {
		dao = new ProdutoDAO();
		produto.setNome(produto.getNome().toUpperCase());
		produto.setMarca(produto.getMarca().toUpperCase());
		produto.setSetor(produto.getSetor().toUpperCase());
		return  dao.atualizarProduto(produto);
	}
	
	public boolean excluirProduto(Produto produto) throws ErroExcluirException {
		dao = new ProdutoDAO();
		return  dao.excluirProduto(produto);
	}

	public List<Produto> consultarComFiltros(ProdutoSeletor produtoSeletor) throws ErroConsultarException {
		dao = new ProdutoDAO();
		return dao.consultarComFiltros(produtoSeletor);
	}
	
}
