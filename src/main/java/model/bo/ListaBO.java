package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.ListaDAO;
import model.exception.ErroConsultarException;
import model.vo.Lista;

public class ListaBO {

	private ListaDAO dao;
	
	public List<Lista> consultarListasBO(int idCliente) throws ErroConsultarException {
		return dao.consultarListasDAO(idCliente);
	}
}
