package br.com.devschool.cliente.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.devschool.cliente.servico.ClienteServico;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.util.Controlador;
import br.com.devschool.util.jpa.JPAUtil;

@ManagedBean
@ViewScoped
public class ClienteCtrl extends Controlador {

	// filtros da pesquisa
	private String nome;
	private String cpf;
	
	private ClienteServico servico;
	private Cliente cliente;
	private List<Cliente> clientes;
	
	// Constrói o controlador e suas dependências...
	public ClienteCtrl() {
		em = JPAUtil.createEntityManager();
		servico = new ClienteServico(em);
		verificarEdicao();
	}
	
	private void verificarEdicao() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente") != null) {
			cliente = (Cliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
		}
	}

	public String atualizar() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", cliente);
		return "cadastroCliente.jsf?faces-redirect=true";
	}
	
	public void salvar() {
		try {
			begin();
			cliente = servico.salvar(cliente);
			commit();
			
			addMensagemInfo("Cliente salvo com sucesso!");
		} catch (Exception e) {
			rollback();
			
			addMensagemError("Ocorreu um erro ao salvar o cliente! Erro: " + e.getMessage());
		} finally {
			close();
			limpar();
		}
	}
	
	public void consultar() {
		try {
			setServico();
			clientes = new ArrayList<Cliente>(servico.consultarPor(nome, cpf));
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar registros! Erro: " + e.getMessage());
		}
	}
	
	public void excluir() {
		try {
			begin();
			servico.excluir(cliente.getId());
			commit();
			
			posExclusao();
		} catch (Exception e) {
			rollback();
			
			addMensagemError("Ocorreu um erro ao excluir o cliente! Erro: " + e.getMessage());
		} finally {
			close();
		}
	}
	
	public void setServico() {
		if (em == null || !em.isOpen()) {
			em = JPAUtil.createEntityManager();
		}
		servico = new ClienteServico(em);
	}
	
	public void limpar() {
		cliente = new Cliente();
		clientes = new ArrayList<Cliente>();
		nome = null;
		cpf = null;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cliente");
	}
	
	private void posExclusao() {
		clientes.remove(cliente);
		cliente = new Cliente();
		
		addMensagemInfo("Cliente excluído com sucesso!");
	}
	
	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		if (clientes == null) {
			clientes = new ArrayList<Cliente>();
		}
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
}
