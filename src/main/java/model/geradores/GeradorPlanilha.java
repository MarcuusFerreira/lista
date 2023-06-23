package model.geradores;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.cj.result.Row;

import model.vo.Produto;

public class GeradorPlanilha {

	public void gerarPlanilhaProdutos(List<Produto> produtos, String caminho) {
		String[] colunasDaPlanilha = { "#", "Marca", "Nome", "Data de Cadastro", "Descrição" };
		XSSFWorkbook planilha = new XSSFWorkbook();

		XSSFSheet abaPlanilha = planilha.createSheet("Produtos");

		Row headerRow = (Row) abaPlanilha.createRow(0);

		for (int i = 0; i < colunasDaPlanilha.length; i++) {
			Cell cell = ((XSSFRow) headerRow).createCell(i);
			cell.setCellValue(colunasDaPlanilha[i]);
		}

		int rowNum = 1;
		for (Produto prod : produtos) {
			Row novaLinha = (Row) abaPlanilha.createRow(rowNum++);

			((XSSFRow) novaLinha).createCell(0).setCellValue(prod.getIdProduto());
			((XSSFRow) novaLinha).createCell(1).setCellValue(prod.getMarca());
			((XSSFRow) novaLinha).createCell(2).setCellValue(prod.getNome());
			((XSSFRow) novaLinha).createCell(3).setCellValue(prod.getDataCadastro());
			((XSSFRow) novaLinha).createCell(4).setCellValue(prod.getSetor());
		}

		for (int i = 0; i < colunasDaPlanilha.length; i++) {
			abaPlanilha.autoSizeColumn(i);
			// TODO tentar não dar autoSize na última coluna
		}
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(caminho + ".xlsx");
			planilha.write(fileOut);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
					planilha.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
