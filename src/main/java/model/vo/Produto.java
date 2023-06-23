package model.vo;

import java.time.LocalDate;

public class Produto {
	
	private Integer idProduto;
	private String nome;
	private LocalDate dataCadastro;
	private String setor;
	private Integer tipoProduto;
	private String marca;
	
	public Produto() {
		
	}
	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getSetor() {
		return setor;
	}
	public void setSetor(String setor) {
		this.setor = setor;
	}
	public Integer getTipoProduto() {
		return tipoProduto;
	}
	public void setTipoProduto(Integer tipoProduto) {
		this.tipoProduto = tipoProduto;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
}
