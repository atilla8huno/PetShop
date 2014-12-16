package br.com.devschool.solicitacao_entrega.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.devschool.entidade.Animal;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.entidade.SolicitacaoEntrega;
import br.com.devschool.solicitacao_entrega.servico.SolicitacaoEntregaServico;
import br.com.devschool.util.Controlador;
import br.com.devschool.util.jpa.JPAUtil;

@ManagedBean
@ViewScoped
public class SolicitacaoEntregaCtrl extends Controlador {

	// filtros da pesquisa
	private Cliente cliente;
	
	private SolicitacaoEntregaServico servico;
	private SolicitacaoEntrega solicitacaoEntrega;
	private List<SolicitacaoEntrega> solicitacaoEntregas;
	private List<Animal> animals;
	
	// Constrói o controlador e suas dependências...
	public SolicitacaoEntregaCtrl() {
		em = JPAUtil.createEntityManager();
		servico = new SolicitacaoEntregaServico(em);
		verificarEdicao();
	}
	
	private void verificarEdicao() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("solicitacaoEntrega") != null) {
			solicitacaoEntrega = (SolicitacaoEntrega) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("solicitacaoEntrega");
			
			cliente = solicitacaoEntrega.getAnimal().getDono();
			consultarAnimais();
		}
	}

	public String atualizar() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitacaoEntrega", solicitacaoEntrega);
		return "cadastroSolicitacaoEntrega.jsf?faces-redirect=true";
	}
	
	public void salvar() {
		try {
			begin();
			solicitacaoEntrega = servico.salvar(solicitacaoEntrega);
			commit();
			
			addMensagemInfo("Solicitação de Entrega salvo com sucesso!");
		} catch (Exception e) {
			rollback();
			
			addMensagemError("Ocorreu um erro ao salvar a Solicitação de Entrega! Erro: " + e.getMessage());
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
			addMensagemError("Erro ao tentar consultar Cliente! Erro: " + e.getMessage());
		}
		return null;
	}
	
	public void consultar() {
		try {
			setServico();
			solicitacaoEntregas = new ArrayList<SolicitacaoEntrega>(servico.consultarPor(cliente));
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar registros! Erro: " + e.getMessage());
		}
	}
	
	public void excluir() {
		try {
			begin();
			servico.excluir(solicitacaoEntrega.getId());
			commit();
			
			posExclusao();
		} catch (Exception e) {
			rollback();
			
			addMensagemError("Ocorreu um erro ao excluir a Solicitação de Entrega! Erro: " + e.getMessage());
		} finally {
			close();
		}
	}
	
	public void consultarAnimais() {
		try {
			animals = new ArrayList<Animal>(servico.consultarAnimaisPor(cliente));
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar Animais! Erro: " + e.getMessage());
		}
	}
	
	public void limpar() {
		solicitacaoEntrega = new SolicitacaoEntrega();
		cliente = new Cliente();
		solicitacaoEntregas = new ArrayList<SolicitacaoEntrega>();
		animals = new ArrayList<Animal>();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("solicitacaoEntrega");
	}
	
	private void posExclusao() {
		solicitacaoEntregas.remove(solicitacaoEntrega);
		solicitacaoEntrega = new SolicitacaoEntrega();
		
		addMensagemInfo("Solicitação de Entrega excluída com sucesso!");
	}
	
	public void setServico() {
		if (em == null || !em.isOpen()) {
			em = JPAUtil.createEntityManager();
		}
		servico = new SolicitacaoEntregaServico(em);
	}
	
	public SolicitacaoEntrega getSolicitacaoEntrega() {
		if (solicitacaoEntrega == null) {
			solicitacaoEntrega = new SolicitacaoEntrega();
		}
		return solicitacaoEntrega;
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

	public void setSolicitacaoEntrega(SolicitacaoEntrega solicitacaoEntrega) {
		this.solicitacaoEntrega = solicitacaoEntrega;
	}

	public List<SolicitacaoEntrega> getSolicitacaoEntregas() {
		if (solicitacaoEntregas == null) {
			solicitacaoEntregas = new ArrayList<SolicitacaoEntrega>();
		}
		return solicitacaoEntregas;
	}

	public void setSolicitacaoEntregas(List<SolicitacaoEntrega> solicitacaoEntregas) {
		this.solicitacaoEntregas = solicitacaoEntregas;
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
