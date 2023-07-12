package model.vo;

public class ProdutoLista extends Produto{

	private UnidadeMedida unidadeMedida;
	private Integer valorMedida;
	private int marcado;
	
	public ProdutoLista() {
		super();
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Integer getValorMedida() {
		return valorMedida;
	}

	public void setValorMedida(Integer valorMedida) {
		this.valorMedida = valorMedida;
	}

	public int getMarcado() {
		return marcado;
	}

	public void setMarcado(int marcado) {
		this.marcado = marcado;
	}
}
