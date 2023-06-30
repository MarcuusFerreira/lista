package model.vo;

import java.time.LocalDate;
import java.util.List;

public class Lista {
	
	private int idLista; //NULL -> 50
	private int idCliente;//5
	private String nomeLista; //A PARTIR DE xyz (2)
	private List<ProdutoLista> produtosLista; //3 produtos
	private LocalDate dataLista; //NEW DATE()
	
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

	public LocalDate getDataLista() {
		return dataLista;
	}

	public void setDataLista(LocalDate dataLista) {
		this.dataLista = dataLista;
	}
	
	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}
}