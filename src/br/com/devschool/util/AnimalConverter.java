package br.com.devschool.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.devschool.animal.servico.AnimalServico;
import br.com.devschool.entidade.Animal;
import br.com.devschool.util.jpa.JPAUtil;

@FacesConverter(forClass = Animal.class, value = "animalConverter")
public class AnimalConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.trim().isEmpty()) {
			AnimalServico servico = new AnimalServico(JPAUtil.createEntityManager());
			return servico.consultarPor(new Integer(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof Animal) {
			return ((Animal) value).getId() != null ? ((Animal) value).getId().toString() : null;
		}
		return null;
	}
}
