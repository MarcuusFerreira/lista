package controller;

import java.util.ArrayList;
import java.util.List;

import model.bo.ListaBO;
import model.exception.*;
import model.seletor.ListaSeletor;
import model.vo.Lista;

public class ListaController {

	private ListaBO bo;

	public List<Lista> consultarListaController(int idCliente) throws ErroConsultarException {
		bo = new ListaBO();
		return bo.consultarListasBO(idCliente);
	}

	public boolean cadastrarListasController(Lista lista) throws ErroCadastroException, ErroListaCadastradaException {
		bo = new ListaBO();
		if(lista.getNomeLista().isBlank()) {
			throw new ErroCadastroException("Nome da Lista em branco");
		}
		lista.setNomeLista(lista.getNomeLista().trim());
		return bo.cadastrarListasBO(lista);
	}

	public List<Lista> consultarListaPorId(int idCliente) throws ErroConsultarException {
		bo = new ListaBO();
		return bo.consultarPorId(idCliente);
	}

	public boolean atualizarListaController(Lista lista) throws ErroAtualizarException {
		bo = new ListaBO();
		lista.setNomeLista(lista.getNomeLista().trim());
		return bo.atualizarLista(lista);
	}

	public boolean excluirListaController(int idLista) throws ErroExcluirException {
		bo = new ListaBO();
		return bo.excluirLista(idLista);
	}

	public ArrayList<String> consultarListasClientePorID(int idCliente) throws ErroConsultarException {
		bo = new ListaBO();
		return bo.consultarListasClientePorIDBO(idCliente);
	}

	public List<Lista> consultarComFiltro(Integer idCliente, ListaSeletor seletor) throws ErroConsultarException{
		bo = new ListaBO();
		return bo.consultarComFiltro(idCliente, seletor);
	}

}
