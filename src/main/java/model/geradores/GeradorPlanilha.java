package model.geradores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.cj.result.Row;

import model.vo.Cliente;
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

	public String gerarPlanilhaClientes(List<Cliente> clientes, String destinoArquivo) {
		HSSFWorkbook arquivoExcel = new HSSFWorkbook();
		HSSFSheet abaPlanilha = arquivoExcel.createSheet("Clientes");

		HSSFRow linhaCabecalho = abaPlanilha.createRow(0);
		linhaCabecalho.createCell(0).setCellValue("Nome");
		linhaCabecalho.createCell(1).setCellValue("CPF");
		linhaCabecalho.createCell(2).setCellValue("Data de Nascimento");
		linhaCabecalho.createCell(3).setCellValue("Nome do Usuário");

		int contadorLinhas = 1;
		for (Cliente c : clientes) {
			HSSFRow novaLinha = abaPlanilha.createRow(contadorLinhas);
			novaLinha.createCell(0).setCellValue(c.getNomeCliente());
			novaLinha.createCell(1).setCellValue(c.getCpf());
			novaLinha.createCell(2).setCellValue(c.getDataNascimento());
			novaLinha.createCell(3).setCellValue(c.getNomeUsuario());
			contadorLinhas++;
		}

		return salvarNoDisco(arquivoExcel, destinoArquivo);
	}

	private String salvarNoDisco(HSSFWorkbook planilha, String caminhoArquivo) {
		String mensagem = "";
		FileOutputStream saida = null;
		String extensao = ".xls";

		try {
			saida = new FileOutputStream(new File(caminhoArquivo + extensao));
			planilha.write(saida);
			mensagem = "Planilha gerada com sucesso!";
		} catch (FileNotFoundException e) {
			mensagem = "Erro ao tentar salvar planilha (sem acesso): " + caminhoArquivo + extensao;
			System.out.println("Causa: " + e.getMessage());
		} catch (IOException e) {
			mensagem = "Erro de I/O ao tentar salvar planilha em: " + caminhoArquivo + extensao;
			System.out.println("Causa: " + e.getMessage());
		} finally {
			if (saida != null) {
				try {
					saida.close();
					planilha.close();
				} catch (IOException e) {
					mensagem = "Erro ao tentar salvar planilha em: " + caminhoArquivo + extensao;
					System.out.println("Causa: " + e.getMessage());
				}
			}
		}

		return mensagem;
	}
}
