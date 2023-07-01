package model.bo;

import java.util.List;

import model.dao.ListaDAO;
import model.exception.*;
import model.vo.Lista;

public class ListaBO {

	private ListaDAO dao;
	
	public boolean cadastrarListasBO(Lista lista) throws ErroCadastroException, ErroListaCadastradaException {
		dao = new ListaDAO();
		lista.setNomeLista(lista.getNomeLista().toUpperCase());
		if(dao.cadastrouMesmoNome(lista.getIdCliente(), lista.getNomeLista())) {
			throw new ErroListaCadastradaException("Esse nome da lista já foi cadastrado");
		}
		return dao.cadastrarLista(lista);
	}
	
	public List<Lista> consultarListasBO(int idCliente) throws ErroConsultarException {
		dao = new ListaDAO();
		return dao.consultarListasDAO(idCliente);
	}
	
	public Lista consultarPorId(int idLista) throws ErroConsultarException {
		dao = new ListaDAO();
		return dao.consultarPorId(idLista);
	}

	public boolean atualizarLista(Lista lista) throws ErroAtualizarException {
		dao = new ListaDAO();
		return dao.atualizarLista(lista);
	}

	public boolean excluirLista(int idLista) throws ErroExcluirException {
		dao = new ListaDAO();
		return dao.excluirLista(idLista);
	}
}
