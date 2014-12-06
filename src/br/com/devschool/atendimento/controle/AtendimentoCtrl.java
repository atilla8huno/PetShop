package br.com.devschool.atendimento.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.devschool.atendimento.servico.AtendimentoServico;
import br.com.devschool.entidade.Atendimento;
import br.com.devschool.util.Servico;

@ManagedBean
@RequestScoped
public class AtendimentoCtrl {

	private Servico<Atendimento> servico;
	private Atendimento atendimento;
	private EntityManager em;
	private List<Atendimento> atendimentos;
	
	public AtendimentoCtrl() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PetShopPU");
		em = factory.createEntityManager();
		servico = new AtendimentoServico(em);
	}
	
	public void salvar() {
		try {
			em.getTransaction().begin();
			atendimento = servico.salvar(atendimento);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
	
	public void excluir() {
		try {
			em.getTransaction().begin();
			servico.excluir(atendimento.getId());
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
	
	public List<Atendimento> consultar() {
		try {
			atendimentos = servico.consultar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return atendimentos;
	}

	public Atendimento getAtendimento() {
		if (atendimento == null) {
			atendimento = new Atendimento();
		}
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public List<Atendimento> getAtendimentos() {
		if (atendimentos == null) {
			atendimentos = new ArrayList<Atendimento>();
		}
		return atendimentos;
	}

	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
	
	
	
}
