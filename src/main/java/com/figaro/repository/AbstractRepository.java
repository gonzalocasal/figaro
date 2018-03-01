package com.figaro.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractRepository {

	
	protected SessionFactory sessionFactory;

	protected Session getCurrentSession(){
	      return sessionFactory.getCurrentSession();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}