package br.com.devschool.atendimento.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.devschool.atendimento.servico.AtendimentoServico;
import br.com.devschool.entidade.Animal;
import br.com.devschool.entidade.Atendimento;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.util.Controlador;
import br.com.devschool.util.jpa.JPAUtil;

@ManagedBean
@ViewScoped
public class AtendimentoCtrl extends Controlador {

	// filtros da pesquisa
	private Cliente cliente;

	private AtendimentoServico servico;
	private Atendimento atendimento;
	private List<Atendimento> atendimentos;
	private List<Animal> animals;

	// Constrói o controlador e suas dependências...
	public AtendimentoCtrl() {
		em = JPAUtil.createEntityManager();
		servico = new AtendimentoServico(em);
		verificarEdicao();
	}

	private void verificarEdicao() {
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("atendimento") != null) {
			atendimento = (Atendimento) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("atendimento");
			
			cliente = atendimento.getAnimal().getDono();
			consultarAnimais();
		}
	}

	public String atualizar() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("atendimento", atendimento);
		return "cadastroAtendimento.jsf?faces-redirect=true";
	}

	public void salvar() {
		try {
			begin();
			atendimento = servico.salvar(atendimento);
			commit();

			addMensagemInfo("Atendimento salvo com sucesso!");
		} catch (Exception e) {
			rollback();

			addMensagemError("Ocorreu um erro ao salvar o atendimento! Erro: " + e.getMessage());
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
			addMensagemError("Erro ao tentar consultar cliente! Erro: "
					+ e.getMessage());
		}
		return null;
	}
	
	public void consultarAnimais() {
		try {
			animals = new ArrayList<Animal>(servico.consultarAnimaisPor(cliente));
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar Animais! Erro: " + e.getMessage());
		}
	}

	public void consultar() {
		try {
			setServico();
			atendimentos = new ArrayList<Atendimento>(servico.consultarPor(cliente));
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar registros! Erro: " + e.getMessage());
		}
	}

	public void excluir() {
		try {
			begin();
			servico.excluir(atendimento.getId());
			commit();

			posExclusao();
		} catch (Exception e) {
			rollback();

			addMensagemError("Ocorreu um erro ao excluir o atendimento! Erro: " + e.getMessage());
		} finally {
			close();
		}
	}

	public void limpar() {
		atendimento = new Atendimento();
		cliente = new Cliente();
		atendimentos = new ArrayList<Atendimento>();
		animals = new ArrayList<Animal>();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("atendimento");
	}

	private void posExclusao() {
		atendimentos.remove(atendimento);
		atendimento = new Atendimento();

		addMensagemInfo("Atendimento excluído com sucesso!");
	}

	public void setServico() {
		if (em == null || !em.isOpen()) {
			em = JPAUtil.createEntityManager();
		}
		servico = new AtendimentoServico(em);
	}

	public Atendimento getAtendimento() {
		if (atendimento == null) {
			atendimento = new Atendimento();
		}
		return atendimento;
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
