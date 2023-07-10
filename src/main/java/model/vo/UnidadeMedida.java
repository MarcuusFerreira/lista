package model.vo;

public enum UnidadeMedida {
	KG (1),
	QTD (2);
	
	private int unidade;
	
	private UnidadeMedida(int unidadeMedida) {
		this.unidade = unidadeMedida;
	}
	
	public int getUnidade() {
		return this.unidade;
	}

	public UnidadeMedida getTipoUnidadeMedida (int valorUnidadeMedida) {
		UnidadeMedida unidadeMedida = null;
		for (UnidadeMedida unidade : UnidadeMedida.values()) {
			if(unidade.getUnidade() == valorUnidadeMedida) {
				unidadeMedida = unidade;
			}
		}
		return unidadeMedida;
	}
}
