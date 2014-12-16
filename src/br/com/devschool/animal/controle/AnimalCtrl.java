package br.com.devschool.animal.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.devschool.animal.servico.AnimalServico;
import br.com.devschool.entidade.Animal;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.util.Controlador;
import br.com.devschool.util.jpa.JPAUtil;

@ManagedBean
@ViewScoped
public class AnimalCtrl extends Controlador {

	// filtros da pesquisa
	private Cliente cliente;
	
	private AnimalServico servico;
	private Animal animal;
	private List<Animal> animals;
	
	// Constrói o controlador e suas dependências...
	public AnimalCtrl() {
		em = JPAUtil.createEntityManager();
		servico = new AnimalServico(em);
		verificarEdicao();
	}
	
	private void verificarEdicao() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("animal") != null) {
			animal = (Animal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("animal");
		}
	}

	public String atualizar() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("animal", animal);
		return "cadastroAnimal.jsf?faces-redirect=true";
	}
	
	public void salvar() {
		try {
			begin();
			animal = servico.salvar(animal);
			commit();
			
			addMensagemInfo("Animal salvo com sucesso!");
		} catch (Exception e) {
			rollback();
			
			addMensagemError("Ocorreu um erro ao salvar o animal! Erro: " + e.getMessage());
		} finally {
			close();
			limpar();
		}
	}
	
	public List<Cliente> autoCompleteCliente(String nome) {
		try {
			setServico();
			return servico.consultarClientes(nome);
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar cliente! Erro: " + e.getMessage());
		}
		return null;
	}
	
	public void consultar() {
		try {
			setServico();
			animals = new ArrayList<Animal>(servico.consultarPor(cliente));
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar registros! Erro: " + e.getMessage());
		}
	}
	
	public void excluir() {
		try {
			begin();
			servico.excluir(animal.getId());
			commit();
			
			posExclusao();
		} catch (Exception e) {
			rollback();
			
			addMensagemError("Ocorreu um erro ao excluir o animal! Erro: " + e.getMessage());
		} finally {
			close();
		}
	}
	
	public void limpar() {
		animal = new Animal();
		cliente = new Cliente();
		animals = new ArrayList<Animal>();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("animal");
	}
	
	private void posExclusao() {
		animals.remove(animal);
		animal = new Animal();
		
		addMensagemInfo("Animal excluído com sucesso!");
	}
	
	public void setServico() {
		if (em == null || !em.isOpen()) {
			em = JPAUtil.createEntityManager();
		}
		servico = new AnimalServico(em);
	}
	
	public Animal getAnimal() {
		if (animal == null) {
			animal = new Animal();
		}
		return animal;
	}
	
	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<Animal> getAnimals() {
		if (animals == null) {
			animals = new ArrayList<Animal>();
		}
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}
}
