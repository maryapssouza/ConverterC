package br.com.conversordec.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.conversordec.entidade.Automata;

public class FuncoesXML {

	//private final static String file_xml = "C:/Users/msouza/Documents/meusArquivos/ProjetoMonografia/LnhaProdPizza.xml";
	
	public static Automata converterXMLtOObject(String arqxml){
		try {							
			BufferedReader br = new BufferedReader(new FileReader(new File(arqxml)));
			String line;
			StringBuilder sb = new StringBuilder();

			while((line=br.readLine())!= null){
			    sb.append(line.trim());
			}
			
			String xml = sb.toString();
			XStream stream = new XStream(new DomDriver());		
			stream.autodetectAnnotations(true);		
			stream.alias("Automata", Automata.class);							
			return (Automata) stream.fromXML(xml);
		} catch (Exception e) {
			Log.getLogger().log( Level.SEVERE, e.toString(), e);
			return null;
		}
	}
	
}
