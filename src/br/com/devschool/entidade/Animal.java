package br.com.devschool.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Animal implements Serializable {

	private static final long serialVersionUID = -6473856833622854456L;

	@Id
	@GeneratedValue
	private Integer id;
	private String nome;
	private String especie;
	private String raca;
	@ManyToOne
	private Cliente dono;

	public Animal() {
		dono = new Cliente();
	}

	public Animal(Integer id, String nome, String especie, String raca,
			Cliente dono) {
		this.id = id;
		this.nome = nome;
		this.especie = especie;
		this.raca = raca;
		this.dono = dono;
	}

	public Cliente getDono() {
		if (dono == null) {
			dono = new Cliente();
		}
		return dono;
	}

	public void setDono(Cliente dono) {
		this.dono = dono;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
