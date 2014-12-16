package br.com.devschool.solicitacao_entrega.servico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.devschool.entidade.Cliente;
import br.com.devschool.entidade.SolicitacaoEntrega;

public class SolicitacaoEntregaDAO {
	
	private EntityManager em;
	
	public SolicitacaoEntregaDAO(EntityManager em) {
		this.em = em;
	}

	protected SolicitacaoEntrega salvar(SolicitacaoEntrega solicitacaoEntrega) {
		em.persist(solicitacaoEntrega);
		return solicitacaoEntrega;
	}
	
	protected SolicitacaoEntrega atualizar(SolicitacaoEntrega solicitacaoEntrega) {
		solicitacaoEntrega = em.merge(solicitacaoEntrega);
		return solicitacaoEntrega;
	}
	
	protected void excluir(Integer id) {
		SolicitacaoEntrega solicitacaoEntrega = em.find(SolicitacaoEntrega.class, id);
		em.remove(solicitacaoEntrega);
	}
	
	protected List<SolicitacaoEntrega> consultar() {
		String query = "SELECT DISTINCT se FROM SolicitacaoEntrega se";
		TypedQuery<SolicitacaoEntrega> typedQuery = em.createQuery(query, SolicitacaoEntrega.class);
		
		return typedQuery.getResultList();
	}
	
	protected List<SolicitacaoEntrega> consultarPor(Cliente cliente) {
		String query = "SELECT DISTINCT se FROM SolicitacaoEntrega se WHERE se.animal.dono.id = :cliente";
		
		TypedQuery<SolicitacaoEntrega> typedQuery = em.createQuery(query, SolicitacaoEntrega.class);
		typedQuery.setParameter("cliente", cliente.getId());
		
		return typedQuery.getResultList();
	}

	protected SolicitacaoEntrega consultarPor(Integer id) {
		return em.find(SolicitacaoEntrega.class, id);
	}
}
