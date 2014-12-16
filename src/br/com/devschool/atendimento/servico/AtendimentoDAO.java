package br.com.devschool.atendimento.servico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.devschool.entidade.Atendimento;
import br.com.devschool.entidade.Cliente;

public class AtendimentoDAO {

private EntityManager em;
	
	public AtendimentoDAO(EntityManager em) {
		this.em = em;
	}
	
	public Atendimento salvarOuAtualizar(Atendimento atendimento) {
		em.getTransaction().begin();
		atendimento = em.merge(atendimento);
		em.getTransaction().commit();
		
		return atendimento;
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
		String query = "SELECT DISTINCT a FROM Atendimento a";
		TypedQuery<Atendimento> typedQuery = em.createQuery(query, Atendimento.class);
		
		return typedQuery.getResultList();
	}

	protected Atendimento consultarPor(Integer id) {
		return em.find(Atendimento.class, id);
	}
	
	protected List<Atendimento> consultarPor(Cliente dono) {
		String query = "SELECT DISTINCT a FROM Atendimento a WHERE a.animal.dono.id = :dono";
		
		TypedQuery<Atendimento> typedQuery = em.createQuery(query, Atendimento.class);
		typedQuery.setParameter("dono", dono.getId());
		
		return typedQuery.getResultList();
	}
}
