package br.com.devschool.atendimento.servico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.devschool.entidade.Atendimento;

public class AtendimentoDAO {

private EntityManager em;
	
	public AtendimentoDAO(EntityManager em) {
		this.em = em;
	}

	protected Atendimento salvar(Atendimento atendimento) {
		em.persist(atendimento);
		return atendimento;
	}
	
	protected Atendimento atualizar(Atendimento atendimento) {
		atendimento = em.merge(atendimento);
		return atendimento;
	}
	
	protected void excluir(Integer id) {
		Atendimento atendimento = em.find(Atendimento.class, id);
		em.remove(atendimento);
	}
	
	protected List<Atendimento> consultar() {
		String query = "SELECT a FROM Atendimento a";
		TypedQuery<Atendimento> typedQuery = em.createQuery(query, Atendimento.class);
		
		return typedQuery.getResultList();
	}

	protected Atendimento consultarPor(Integer id) {
		return em.find(Atendimento.class, id);
	}
}
