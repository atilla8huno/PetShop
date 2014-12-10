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
		if (cliente.getId() != null && cliente.getId() > 0) {
			return atualizar(cliente);
		}
		cliente = dao.salvar(cliente);
		return cliente;
	}
	
	public Cliente atualizar(Cliente cliente) {
		cliente = dao.atualizar(cliente); 
		return cliente;
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
