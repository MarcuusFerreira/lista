package model.seletor;

import java.time.LocalDateTime;

public class ProdutoSeletor extends BaseSeletor {

	private String nome;
	private LocalDateTime dataCadastroInicial;
	private LocalDateTime dataCadastroFim;
	private String setor;
	private String marca;

	@Override
	public boolean temFiltro() {
		return (this.nome != null && this.nome.trim().length() > 0)
				|| (this.setor != null && this.setor.trim().length() > 0)
				|| (this.marca != null && this.setor.trim().length() > 0) 
				|| this.dataCadastroInicial != null
				|| this.dataCadastroFim != null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDateTime getDataCadastroInicial() {
		return dataCadastroInicial;
	}

	public void setDataCadastroInicial(LocalDateTime dataCadastroInicial) {
		this.dataCadastroInicial = dataCadastroInicial;
	}

	public LocalDateTime getDataCadastroFim() {
		return dataCadastroFim;
	}

	public void setDataCadastroFim(LocalDateTime dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

}
