package br.com.devschool.animal.servico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.devschool.entidade.Animal;
import br.com.devschool.entidade.Cliente;

public class AnimalDAO {
	
	private EntityManager em;
	
	public AnimalDAO(EntityManager em) {
		this.em = em;
	}

	protected Animal salvar(Animal animal) {
		em.persist(animal);
		return animal;
	}
	
	protected Animal atualizar(Animal animal) {
		animal = em.merge(animal);
		return animal;
	}
	
	protected void excluir(Integer id) {
		Animal animal = em.find(Animal.class, id);
		em.remove(animal);
	}
	
	protected List<Animal> consultar() {
		String query = "SELECT DISTINCT a FROM Animal a";
		TypedQuery<Animal> typedQuery = em.createQuery(query, Animal.class);
		
		return typedQuery.getResultList();
	}
	
	protected List<Animal> consultarPor(Cliente dono) {
		String query = "SELECT DISTINCT a FROM Animal a WHERE a.dono.id = :dono";
		
		TypedQuery<Animal> typedQuery = em.createQuery(query, Animal.class);
		typedQuery.setParameter("dono", dono.getId());
		
		return typedQuery.getResultList();
	}

	protected Animal consultarPor(Integer id) {
		return em.find(Animal.class, id);
	}
}
