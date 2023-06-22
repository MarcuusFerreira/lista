package model.vo;

import java.time.LocalDate;
import java.util.List;

public class Lista {
	
	private int idLista;
	private int idCliente;
	private List<ProdutosLista> produtosLista;
	private LocalDate dataLista;
	
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

	public List<ProdutosLista> getProdutosListas() {
		return produtosLista;
	}

	public void setProdutos(List<ProdutosLista> produtosListas) {
		this.produtosLista = produtosListas;
	}

	public LocalDate getDataLista() {
		return dataLista;
	}

	public void setDataLista(LocalDate dataLista) {
		this.dataLista = dataLista;
	}
}