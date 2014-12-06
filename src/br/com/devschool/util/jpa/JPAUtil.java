package br.com.devschool.util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory;
	
	static {
		factory = Persistence.createEntityManagerFactory("PetShopPU");
	}
	
	public static EntityManager createEntityManager() {
		return factory.createEntityManager();
	}
}
