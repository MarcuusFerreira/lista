package controller;

import java.util.ArrayList;
import java.util.List;

import model.bo.ListaBO;
import model.exception.ErroConsultarException;
import model.vo.Lista;

public class ListaController {
	
	private ListaBO bo;

	public List<Lista> consultarListaController(int idCliente) throws ErroConsultarException {
		bo = new ListaBO();
		return bo.consultarListasBO(idCliente);
	}
	
	public boolean cadastrarListasController(Lista lista) {
		boolean cadastrou = false;
		return cadastrou;
	}

	public ArrayList<String> consultarListasClientePorID(int idCliente) throws ErroConsultarException {
		bo = new ListaBO();
		return bo.consultarListasClientePorIDBO(idCliente);
	}
}
