package br.com.devschool.solicitacao_entrega.servico;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.devschool.animal.servico.AnimalServico;
import br.com.devschool.cliente.servico.ClienteServico;
import br.com.devschool.entidade.Animal;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.entidade.SolicitacaoEntrega;
import br.com.devschool.util.Servico;

public class SolicitacaoEntregaServico implements Servico<SolicitacaoEntrega> {
	
	private SolicitacaoEntregaDAO dao;
	private ClienteServico clienteServico;
	private AnimalServico animalServico;
	
	public SolicitacaoEntregaServico(EntityManager em) {
		dao = new SolicitacaoEntregaDAO(em);
		clienteServico = new ClienteServico(em);
		animalServico = new AnimalServico(em);
	}

	public SolicitacaoEntrega salvar(SolicitacaoEntrega solicitacaoEntrega) {
		if (solicitacaoEntrega.getId() != null && solicitacaoEntrega.getId() > 0) {
			return atualizar(solicitacaoEntrega);
		}
		solicitacaoEntrega = dao.salvar(solicitacaoEntrega);
		return solicitacaoEntrega;
	}
	
	public SolicitacaoEntrega atualizar(SolicitacaoEntrega solicitacaoEntrega) {
		solicitacaoEntrega = dao.atualizar(solicitacaoEntrega); 
		return solicitacaoEntrega;
	}
	
	public void excluir(Integer id) {
		dao.excluir(id);
	}
	
	public List<SolicitacaoEntrega> consultar() {
		return dao.consultar();
	}

	public SolicitacaoEntrega consultarPor(Integer id) {
		return dao.consultarPor(id);
	}

	public List<Cliente> consultarClientes(String nome) {
		nome = nome.concat("%");
		return clienteServico.consultarPor(nome);
	}

	public List<SolicitacaoEntrega> consultarPor(Cliente cliente) {
		if (cliente == null || cliente.getId() == null) {
			throw new IllegalArgumentException("É obrigatório selecionar um Cliente.");
		}
		return dao.consultarPor(cliente);
	}
	
	public List<Animal> consultarAnimaisPor(Cliente cliente) {
		return animalServico.consultarPor(cliente);
	}
}
