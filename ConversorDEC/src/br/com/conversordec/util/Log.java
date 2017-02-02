package br.com.conversordec.util;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
	private static Logger log = null;

	private Log() {

	}

	public static Logger getLogger() {
		try {
			if (log == null) {
				log = Logger.getLogger("Implog-SetaDigital");			
				FileHandler fh = new FileHandler("C:\\Users\\msouza\\Documents\\meusArquivos\\ProjetoMonografia\\logErros.log", true);
				log.addHandler(fh);
				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);
			}
			return log;
		} catch (Exception ex) {
			return log;
		}

	}

}
