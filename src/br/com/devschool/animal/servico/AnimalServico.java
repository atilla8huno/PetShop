package br.com.devschool.animal.servico;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.devschool.cliente.servico.ClienteServico;
import br.com.devschool.entidade.Animal;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.util.Servico;

public class AnimalServico implements Servico<Animal> {
	
	private AnimalDAO dao;
	private ClienteServico clienteServico;
	
	public AnimalServico(EntityManager em) {
		dao = new AnimalDAO(em);
		clienteServico = new ClienteServico(em);
	}

	public Animal salvar(Animal animal) {
		return dao.salvar(animal);
	}
	
	public Animal atualizar(Animal animal) {
		return dao.atualizar(animal);
	}
	
	public void excluir(Integer id) {
		dao.excluir(id);
	}
	
	public List<Animal> consultar() {
		return dao.consultar();
	}

	public Animal consultarPor(Integer id) {
		return dao.consultarPor(id);
	}

	public List<Cliente> consultarClientes(String search) {
		search = search.concat("%");
		return clienteServico.consultarPor(search);
	}

	public List<Animal> consultarPor(Cliente cliente) {
		if (cliente == null || cliente.getId() == null) {
			throw new IllegalArgumentException("É obrigatório selecionar um Cliente.");
		}
		return dao.consultarPor(cliente);
	}
}
