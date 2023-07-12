package controller;

import java.util.List;

import model.bo.ProdutoBO;
import model.exception.ErroAtualizarException;
import model.exception.ErroCadastroException;
import model.exception.ErroConsultarException;
import model.exception.ErroExcluirException;
import model.seletor.ProdutoSeletor;
import model.vo.Produto;

public class ProdutoController {

	private ProdutoBO bo;
	private static final int TAMANHO_MINIMO_NOME = 4;
	private static final int TAMANHO_MINIMO_SETOR = 4;
	private static final int TAMANHO_MINIMO_MARCA = 3;

	public List<Produto> consultarProdutos() throws ErroConsultarException {
		bo = new ProdutoBO();
		return bo.consultarProdutosBO();
	}

	public boolean cadastrarProduto(Produto produto) throws ErroCadastroException {
		bo = new ProdutoBO();
		if (!validarCamposEmBranco(produto)) {
			throw new ErroCadastroException("Existem campos em branco");
		}
		if (produto.getNome().trim().length() < TAMANHO_MINIMO_NOME) {
			throw new ErroCadastroException("Existem campos em branco");
		}
		if (produto.getSetor().trim().length() < TAMANHO_MINIMO_SETOR) {
			throw new ErroCadastroException("Existem campos em branco");
		}
		if (produto.getMarca().trim().length() < TAMANHO_MINIMO_MARCA) {
			throw new ErroCadastroException("Existem campos em branco");
		}
		produto.setNome(produto.getNome().trim());
		produto.setMarca(produto.getMarca().trim());
		produto.setSetor(produto.getSetor().trim());
		return bo.cadastrarProduto(produto);
	}

	public boolean atualizarProduto(Produto produto) throws ErroAtualizarException {
		bo = new ProdutoBO();
		if (!validarCamposEmBranco(produto)) {
			throw new ErroAtualizarException("Existem campos em branco");
		}
		if (produto.getNome().trim().length() < TAMANHO_MINIMO_NOME) {
			throw new ErroAtualizarException("Existem campos em branco");
		}
		if (produto.getSetor().trim().length() < TAMANHO_MINIMO_SETOR) {
			throw new ErroAtualizarException("Existem campos em branco");
		}
		if (produto.getMarca().trim().length() < TAMANHO_MINIMO_MARCA) {
			throw new ErroAtualizarException("Existem campos em branco");
		}
		return bo.atualizarProduto(produto);
	}
	
	public boolean excluirProduto(Produto produto) throws ErroExcluirException {
		bo = new ProdutoBO();
		return bo.excluirProduto(produto);
	}
	
	public Produto consultarProduto(int idProduto) throws ErroConsultarException {
		bo = new ProdutoBO();
		return bo.consultarPorId(idProduto);
	}

	private boolean validarCamposEmBranco(Produto produto) {
		boolean validado = true;
		if (produto.getNome().isBlank() || 
				produto.getMarca().isBlank() || 
				produto.getSetor().isBlank() || 
				produto.getDataCadastro() == null) {
			validado = false;
		}
		return validado;
	}

	public List<Produto> consultarComFiltro(ProdutoSeletor produtoSeletor) throws ErroConsultarException {
		bo = new ProdutoBO();
		return bo.consultarComFiltros(produtoSeletor);
	}

	public int contarTotalRegistros() {
		return 0;
	}
}
