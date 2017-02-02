package br.com.conversordec.principal;

import java.util.Vector;
import javax.swing.JOptionPane;
import br.com.conversordec.entidade.Automata;
import br.com.conversordec.entidade.Automatos;
import br.com.conversordec.entidade.Estados;
import br.com.conversordec.entidade.Eventos;
import br.com.conversordec.entidade.Transicoes;
import br.com.conversordec.util.FuncoesC;
import br.com.conversordec.util.FuncoesXML;

public class Principal {

	Vector<Transicoes> vectorTransicoes = new Vector<Transicoes>();
	Vector<String> vectorEventosNaoControlaveis = new Vector<String>();
	Vector<String> vectorEventosControlaveis = new Vector<String>();
	
	Automata automata;				
	String estadoInicial = "";
	int posicao = 1;
	int NTRANS = 0;
	int NESTADOS = 0;	
	String xml = "";
	
	private Automata getXMLtoObject(){ 
	   automata = FuncoesXML.converterXMLtOObject(xml);
	   return automata;	
	}
	
	private void setTransicoes(){
		
		Boolean percorre = false;		
		if (!automata.getAutomatos().isEmpty()) { // if 1
			if (automata.getAutomatos().size() > 0) { // if 2
				
				for (Automatos aut : automata.getAutomatos()) { // for 1
					NTRANS = aut.getTransicoes().size();
					NESTADOS = aut.getEstados().size();					
					
					int count=0;
					for (Eventos e : aut.getEventos()) {						
						if (count % 2 == 0 ) {
							vectorEventosNaoControlaveis.addElement(e.getId());							
						}else{
							vectorEventosControlaveis.addElement(e.getId());
						}
						count++;
					}
					
					for(Estados e : aut.getEstados()){
						if (e.getInitial() != null) {
							if (e.getInitial()) {
								estadoInicial = e.getId();								
							}
						}
					}	
															
					for(Transicoes t : aut.getTransicoes()){
						
						Transicoes trans = new Transicoes();						
						if (t.getSource().equals(estadoInicial) && !percorre) {	
							trans = new Transicoes();
							
							trans.setPosicao(posicao);
							trans.setSource(t.getSource());
							trans.setDest(t.getDest());
							trans.setEvent(t.getEvent());		
							
							vectorTransicoes.addElement(trans);
							percorre = true;
							continue;
						}	
						if (percorre) {

							if (!vectorTransicoes.contains(t.getEvent())) {
								
								posicao++;
								trans.setPosicao(posicao);
								trans.setSource(t.getSource());
								trans.setDest(t.getDest());
								trans.setEvent(t.getEvent());

								vectorTransicoes.addElement(trans);
							}
						}						
					}													
				}	// fim for 1
			} // fim if 2
		} // fim if 1
		
	}
	
	public static void main(String[] args) {
				
		Principal p = new Principal();		
		p.xml = JOptionPane.showInputDialog("Informe o local e o arquivo xml. (ex.: C://meuarquivo.xml");
		
		if (p.xml != "") {			
		    p.getXMLtoObject();
			p.setTransicoes();
			
			FuncoesC fc = new FuncoesC();
			fc.setNtrans(p.NTRANS);
			fc.setNestados(p.NESTADOS);
			fc.setVectorTransicoes(p.vectorTransicoes);
			fc.setVectorEvtControl(p.vectorEventosControlaveis);
			fc.setVectorEvtNaoControl(p.vectorEventosNaoControlaveis);
			
			fc.converterJavaToC();
		
		}else{
			JOptionPane.showMessageDialog(null, "Arquivo inválido.");
		}		
	}

}



