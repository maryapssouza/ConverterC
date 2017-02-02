package br.com.conversordec.entidade;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Automaton")
public class Automatos {
	
	@XStreamAlias("States")
	private ArrayList<Estados> estados;
	
	@XStreamAlias("Events")
	private ArrayList<Eventos> eventos;
	
	@XStreamAlias("Transitions")
	private ArrayList<Transicoes> transicoes;
	
	
	public ArrayList<Estados> getEstados() {
		return estados;
	}
	public void setEstados(ArrayList<Estados> estados) {
		this.estados = estados;
	}
	public ArrayList<Eventos> getEventos() {
		return eventos;
	}
	public void setEventos(ArrayList<Eventos> eventos) {
		this.eventos = eventos;
	}
	public ArrayList<Transicoes> getTransicoes() {
		return transicoes;
	}
	public void setTransicoes(ArrayList<Transicoes> transicoes) {
		this.transicoes = transicoes;
	}
	
}
