package br.com.conversordec.entidade;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Automata")
public class Automata {

	@XStreamAlias("Automaton")
	@XStreamImplicit
	private List<Automatos> automatos;

	public List<Automatos> getAutomatos() {
		return automatos;
	}

	public void setAutomatos(List<Automatos> automatos) {
		this.automatos = automatos;
	}
	
}
