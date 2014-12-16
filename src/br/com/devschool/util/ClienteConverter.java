package br.com.devschool.util;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.devschool.cliente.servico.ClienteServico;
import br.com.devschool.entidade.Cliente;
import br.com.devschool.util.jpa.JPAUtil;

@FacesConverter(forClass = Cliente.class, value = "clienteConverter")
@SessionScoped
public class ClienteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente cliente = null;
		
		if (value != null) {
			Servico<Cliente> servico = new ClienteServico(JPAUtil.createEntityManager());
			cliente = servico.consultarPor(new Integer(value));
		}
		return cliente;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((Cliente) value).getId() != null ? ((Cliente) value).getId().toString() : null;
		}
		return null;
	}
}
