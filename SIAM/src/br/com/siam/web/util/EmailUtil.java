package br.com.siam.web.util;

import java.util.Date;
import java.util.Properties;



import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.hsqldb.Session;

import br.com.siam.util.*;

public class EmailUtil {

	public void enviarEmail(String de, String para, String assunto, String mensagem) throws UtilException {
		try {
			Context initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:comp/env");
			Session session = (Session) envContext.lookup("mail/Session");
			
			SimpleEmail email = new SimpleEmail();
			if (de != null) {
				email.setFrom(de);
			} else {
//				Properties p = session.getProperties();
//				email.setFrom(p.getProperty("mail.smtp.user"));
			}
			email.addTo(para);
			email.setSubject(assunto);
			email.setMsg(mensagem);
			email.setSentDate(new Date());
			email.send();
		} catch (EmailException e) {
			throw new UtilException(e);
		} catch (NamingException e) {
			throw new UtilException(e);
		}
	}
}