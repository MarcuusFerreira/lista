package model.seletor;

import java.time.LocalDate;

public class ListaSeletor extends BaseSeletor {

	private String nome;
	private LocalDate dataCadastroInicio;
	private LocalDate dataCadastroFim;

	@Override
	public boolean temFiltro() {
		return (this.nome.trim().length() > 0) || dataCadastroInicio != null || dataCadastroFim != null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataCadastroInicio() {
		return dataCadastroInicio;
	}

	public void setDataCadastroInicio(LocalDate dataCadastroInicio) {
		this.dataCadastroInicio = dataCadastroInicio;
	}

	public LocalDate getDataCadastroFim() {
		return dataCadastroFim;
	}

	public void setDataCadastroFim(LocalDate dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}

}
