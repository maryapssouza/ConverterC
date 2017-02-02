package br.com.conversordec.entidade;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Transition")
public class Transicoes {

	@XStreamAsAttribute
	private String source;
	
	@XStreamAsAttribute
	private String dest;
	
	@XStreamAsAttribute
	private String event;
	
	private int posicao;
	
	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public Transicoes(){
		
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
}
