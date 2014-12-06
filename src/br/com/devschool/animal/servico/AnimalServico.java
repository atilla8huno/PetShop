package br.com.devschool.animal.servico;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.devschool.entidade.Animal;
import br.com.devschool.util.Servico;

public class AnimalServico implements Servico<Animal> {
	
	private AnimalDAO dao;
	
	public AnimalServico(EntityManager em) {
		dao = new AnimalDAO(em);
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
}
