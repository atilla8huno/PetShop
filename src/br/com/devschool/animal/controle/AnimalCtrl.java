package br.com.devschool.animal.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.devschool.animal.servico.AnimalServico;
import br.com.devschool.entidade.Animal;
import br.com.devschool.util.Servico;

@ManagedBean
@RequestScoped
public class AnimalCtrl {

	private Servico<Animal> servico;
	private Animal animal;
	private EntityManager em;
	private List<Animal> animais;
	
	public AnimalCtrl() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PetShopPU");
		em = factory.createEntityManager();
		servico = new AnimalServico(em);
	}
	
	public void salvar() {
		try {
			em.getTransaction().begin();
			animal = servico.salvar(animal);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
	
	public void excluir() {
		try {
			em.getTransaction().begin();
			servico.excluir(animal.getId());
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
	
	public List<Animal> consultar() {
		try {
			animais = servico.consultar();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return animais;
	}

	public Animal getAnimal() {
		if (animal == null) {
			animal = new Animal();
		}
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<Animal> getAnimais() {
		if (animais == null) {
			animais = new ArrayList<Animal>();
		}
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
}
