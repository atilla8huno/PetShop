package br.com.devschool.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.devschool.util.jpa.JPAUtil;

public abstract class Controlador {
	
	protected EntityManager em;
	
	private static Severity ERROR	= FacesMessage.SEVERITY_ERROR;
	private static Severity INFO	= FacesMessage.SEVERITY_INFO;
	private static Severity WARN	= FacesMessage.SEVERITY_WARN;

	public abstract void salvar();
	public abstract void limpar();
	public abstract String atualizar();
	public abstract void excluir();
	public abstract void consultar();

	protected void begin() {
		if (em == null || !em.isOpen()) {
			em = JPAUtil.createEntityManager();
		}
		
		em.getTransaction().begin();
	}
	
	protected void commit() {
		em.getTransaction().commit();
	}
	
	protected void close() {
		if (em.isOpen()) {
			em.close();
		}
	}
	
	protected void rollback() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
	}
	
	public static void addMensagem(Severity severity, String titulo, String msg) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.getExternalContext().getFlash().setKeepMessages(Boolean.TRUE);

		context.addMessage(null, new FacesMessage(severity, titulo, msg));
	}

	protected HttpSession getSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();  
		HttpSession httpSession = request.getSession(false);
		
		return httpSession;
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
