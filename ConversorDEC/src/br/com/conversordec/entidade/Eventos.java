package br.com.conversordec.entidade;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Event")
public class Eventos {

	@XStreamAsAttribute
	private String id;
	
	@XStreamAsAttribute
	private String label;
	
	public Eventos(){
		
	}
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
}
