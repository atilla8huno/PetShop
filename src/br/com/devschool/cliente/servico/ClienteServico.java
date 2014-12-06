package br.com.devschool.cliente.servico;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.devschool.entidade.Cliente;
import br.com.devschool.util.Servico;

public class ClienteServico implements Servico<Cliente> {
	
	private ClienteDAO dao;
	
	public ClienteServico(EntityManager em) {
		dao = new ClienteDAO(em);
	}

	public Cliente salvar(Cliente cliente) {
		return cliente = dao.salvar(cliente);
	}
	
	public Cliente atualizar(Cliente cliente) {
		return dao.atualizar(cliente);
	}
	
	public void excluir(Integer id) {
		dao.excluir(id);
	}
	
	public List<Cliente> consultar() {
		return dao.consultar();
	}

	public Cliente consultarPor(Integer id) {
		return dao.consultarPor(id);
	}
}
