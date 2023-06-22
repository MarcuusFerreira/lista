package model.vo;

public class ProdutosLista extends Produto{

	private UnidadeMedida unidadeMedida;
	private double valorMedida;
	private int marcado;
	private String obs;
	
	public ProdutosLista() {
		super();
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public double getValorMedida() {
		return valorMedida;
	}

	public void setValorMedida(double valorMedida) {
		this.valorMedida = valorMedida;
	}

	public int getMarcado() {
		return marcado;
	}

	public void setMarcado(int marcado) {
		this.marcado = marcado;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
}
