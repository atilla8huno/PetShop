package br.com.devschool.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.devschool.util.jpa.JPAUtil;

public abstract class Controlador {
	
	private static Severity ERROR	= FacesMessage.SEVERITY_ERROR;
	private static Severity INFO	= FacesMessage.SEVERITY_INFO;
	private static Severity WARN	= FacesMessage.SEVERITY_WARN;

	public abstract void salvar();
	public abstract void limpar();
	public abstract void atualizar();
	public abstract void excluir();
	public abstract void consultar();

	protected void begin(EntityManager em) {
		if (em == null || !em.isOpen()) {
			em = JPAUtil.createEntityManager();
		}
		
		em.getTransaction().begin();
	}
	
	protected void commit(EntityManager em) {
		em.getTransaction().commit();
	}
	
	protected void close(EntityManager em) {
		if (em.isOpen()) {
			em.close();
		}
	}
	
	protected void rollback(EntityManager em) {
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
	}
	
	public static void addMensagem(Severity severity, String titulo, String msg) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.getExternalContext().getFlash().setKeepMessages(Boolean.TRUE);

		context.addMessage(null, new FacesMessage(severity, titulo, msg));
	}

	public static void addMensagemError(String msg) {
		addMensagem(ERROR, "ERRO", msg);
	}

	public static void addMensagemInfo(String msg) {
		addMensagem(INFO, "INFO", msg);
	}

	public static void addMensagemWarn(String msg) {
		addMensagem(WARN, "AVISO", msg);
	}
}
