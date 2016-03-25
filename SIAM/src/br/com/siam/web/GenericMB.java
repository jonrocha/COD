package br.com.siam.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GenericMB {
	
	private static Logger logger = LogManager.getLogger(UsuarioMB.class.getName());

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		GenericMB.logger = logger;
	}

}
