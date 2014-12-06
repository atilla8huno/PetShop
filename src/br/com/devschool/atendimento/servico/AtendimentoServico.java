package br.com.devschool.atendimento.servico;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.devschool.entidade.Atendimento;
import br.com.devschool.util.Servico;

public class AtendimentoServico implements Servico<Atendimento> {
	
	private AtendimentoDAO dao;
	
	public AtendimentoServico(EntityManager em) {
		dao = new AtendimentoDAO(em);
	}

	public Atendimento consultarPor(Integer id) {
		return dao.consultarPor(id);
	}
	
	public Atendimento salvar(Atendimento atendimento) {
		return dao.salvar(atendimento);
	}
	
	public Atendimento atualizar(Atendimento atendimento) {
		return dao.atualizar(atendimento);
	}
	
	public void excluir(Integer id) {
		dao.excluir(id);
	}
	
	public List<Atendimento> consultar() {
		return dao.consultar();
	}
}
