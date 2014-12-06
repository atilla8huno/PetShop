package br.com.devschool.cliente.servico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.devschool.entidade.Cliente;

public class ClienteDAO {

	private EntityManager em;
	
	public ClienteDAO(EntityManager em) {
		this.em = em;
	}

	protected Cliente salvar(Cliente cliente) {
		em.persist(cliente);
		return cliente;
	}
	
	protected Cliente atualizar(Cliente cliente) {
		cliente = em.merge(cliente);
		return cliente;
	}
	
	protected void excluir(Integer id) {
		Cliente cliente = em.find(Cliente.class, id);
		em.remove(cliente);
	}
	
	protected List<Cliente> consultar() {
		String query = "SELECT c FROM Cliente c";
		TypedQuery<Cliente> typedQuery = em.createQuery(query, Cliente.class);
		
		return typedQuery.getResultList();
	}

	protected Cliente consultarPor(Integer id) {
		return em.find(Cliente.class, id);
	}
}
