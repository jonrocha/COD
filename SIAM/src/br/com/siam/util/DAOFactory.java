package br.com.siam.util;

import br.com.siam.dao.UsuarioDAO;
import br.com.siam.dao.UsuarioDAOHibernate;

public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO() {
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}

}
