package br.com.conversordec.entidade;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("State")
public class Estados {

	@XStreamAsAttribute
	private String id;
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private Boolean initial;
	
	@XStreamAsAttribute
	private String accepting;
	
	public Estados(){
		
	}	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getInitial() {
		return initial;
	}
	public void setInitial(Boolean initial) {
		this.initial = initial;
	}
	public String getAccepting() {
		return accepting;
	}
	public void setAccepting(String accepting) {
		this.accepting = accepting;
	}
	
	
	
}
