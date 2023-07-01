package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.ListaDAO;
import model.exception.ErroCadastroException;
import model.exception.ErroConsultarException;
import model.vo.Lista;

public class ListaBO {

	private ListaDAO dao;
	
	public boolean cadastrarListasBO(Lista lista) throws ErroCadastroException {
		dao = new ListaDAO();
		lista.setNomeLista(lista.getNomeLista().toUpperCase());
		if(dao.cadastrouMesmoNome(lista.getIdCliente(), lista.getNomeLista()))
			
		return dao.cadastrarLista(lista);
		return false;
		
	}
	
	public List<Lista> consultarListasBO(int idCliente) throws ErroConsultarException {
		dao = new ListaDAO();
		return dao.consultarListasDAO(idCliente);
	}
	
	public Lista consultarPorIdBO(int idLista) throws ErroConsultarException {
		
		return dao.consultarPorId(idLista);
	}

	public ArrayList<String> consultarListasClientePorIDBO(int idCliente) throws ErroConsultarException {
		dao = new ListaDAO();
		return dao.consultarListasClientePorIDDAO(idCliente);
	}
}
