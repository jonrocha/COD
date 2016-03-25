package br.com.siam.util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DAOException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mensagemAmigavel;

	private static Logger logger = LogManager.getLogger(DAOException.class.getName());
	
	public DAOException() {
	}

	public DAOException(String message) {
		super(message);
		logger.log(Level.ERROR, message);
	}

	public DAOException(Throwable cause) {
		super(cause);
		logger.log(Level.ERROR, null, cause);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
		logger.log(Level.ERROR, message, cause);
	}

	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		logger.log(Level.ERROR, message, cause);
	}

	public String getMensagemAmigavel() {
		return mensagemAmigavel;
	}

}
