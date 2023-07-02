package model.vo;

import java.time.LocalDateTime;
import java.util.List;

public class Lista {
	
	private int idLista;
	private int idCliente;
	private String nomeLista;
	private List<ProdutoLista> produtosLista;
	private LocalDateTime dataLista;
	
	public Lista() {
		
	}

	public int getIdLista() {
		return idLista;
	}

	public void setIdLista(int idLista) {
		this.idLista = idLista;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public List<ProdutoLista> getProdutosListas() {
		return produtosLista;
	}

	public void setProdutos(List<ProdutoLista> produtosListas) {
		this.produtosLista = produtosListas;
	}

	public LocalDateTime getDataLista() {
		return dataLista;
	}

	public void setDataLista(LocalDateTime dataLista) {
		this.dataLista = dataLista;
	}
	
	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}
}