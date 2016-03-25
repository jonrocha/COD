package br.com.siam.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.siam.entidade.Produto;

public class ProdutoDAOHibernate {

	private Session	session;

	public void setSession(Session session) {
		this.session = session;
	}

	public void salvar(Produto produto) {
		produto.setId(null);
		this.session.saveOrUpdate(produto);
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> listar() {
		return this.session.createCriteria(Produto.class).list();
	}

}
