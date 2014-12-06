package br.com.devschool.cliente.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.com.devschool.cliente.servico.ClienteServico;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.util.Controlador;
import br.com.devschool.util.Servico;
import br.com.devschool.util.jpa.JPAUtil;

@ManagedBean
@ViewScoped
public class ClienteCtrl extends Controlador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// filtros da pesquisa
	private String nome;
	private String cpf;
	
	private Servico<Cliente> servico;
	private Cliente cliente;
	private EntityManager em;
	private List<Cliente> clientes;
	
	// Constrói o controlador e suas dependências...
	public ClienteCtrl() {
		em = JPAUtil.createEntityManager();
		servico = new ClienteServico(em);
		
		limpar();
	}
	
	public void salvar() {
		try {
			begin(em);
			cliente = servico.salvar(cliente);
			commit(em);
			
			addMensagemInfo("Cliente salvo com sucesso!");
		} catch (Exception e) {
			rollback(em);
			
			addMensagemError("Ocorreu um erro ao salvar o cliente! Erro: " + e.getMessage());
		} finally {
			close(em);
			limpar();
		}
	}
	
	public void atualizar() {
		try {
			begin(em);
			cliente = servico.atualizar(cliente);
			commit(em);
			
			addMensagemInfo("Cliente atualizado com sucesso!");
		} catch (Exception e) {
			rollback(em);
			
			addMensagemError("Ocorreu um erro ao atualizar o cliente! Erro: " + e.getMessage());
		} finally {
			close(em);
			limpar();
		}
	}
	
	public void consultar() {
		try {
			clientes = new ArrayList<Cliente>(servico.consultar());
		} catch (Exception e) {
			addMensagemError("Erro ao tentar consultar registros! Erro: " + e.getMessage());
		}
	}
	
	public void excluir() {
		try {
			begin(em);
			servico.excluir(cliente.getId());
			commit(em);
			
			addMensagemInfo("Cliente excluído com sucesso!");
		} catch (Exception e) {
			rollback(em);
			
			addMensagemError("Ocorreu um erro ao excluir o cliente! Erro: " + e.getMessage());
		} finally {
			close(em);
			limpar();
		}
	}
	
	public void limpar() {
		cliente = new Cliente();
		clientes = new ArrayList<Cliente>();
		nome = null;
		cpf = null;
	}
	
	protected void begin(EntityManager em) {
		if (em == null || !em.isOpen()) {
			em = JPAUtil.createEntityManager();
			servico = new ClienteServico(em);
		}
		
		em.getTransaction().begin();
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
