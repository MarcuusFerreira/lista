package model.geradores;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.entity.Produto;

public class GeradorPlanilha {

	public void gerarPlanilhaProdutos(List<Produto> produtos, String caminho) {
		String[] colunasDaPlanilha = { "#", "Marca", "Nome", "Data de Cadastro", "Descrição" };
		XSSFWorkbook planilha = new XSSFWorkbook();
	}
}
