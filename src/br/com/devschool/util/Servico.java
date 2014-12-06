package br.com.devschool.util;

import java.util.List;

public interface Servico<T> {

	T salvar(T entidade);
	T atualizar(T entidade);
	void excluir(Integer id);
	List<T> consultar();
	T consultarPor(Integer id);
}
