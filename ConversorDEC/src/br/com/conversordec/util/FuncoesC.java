package br.com.conversordec.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import br.com.conversordec.entidade.Transicoes;

public class FuncoesC {

	static String content = "";
	
	private int ntrans;
	private int nestados;
	private int define;
	private int value;
	private Vector<Transicoes> vectorTransicoes;
	private Vector<String> vectorEvtNaoControl;
	private Vector<String> vectorEvtControl;
	
	public int getNtrans() {
		return ntrans;
	}

	public void setNtrans(int ntrans) {
		this.ntrans = ntrans;
	}

	public int getNestados() {
		return nestados;
	}

	public void setNestados(int nestados) {
		this.nestados = nestados;
	}

	public int getDefine() {
		return define;
	}

	public void setDefine(int define) {
		this.define = define;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Vector<Transicoes> getVectorTransicoes() {
		return vectorTransicoes;
	}

	public void setVectorTransicoes(Vector<Transicoes> vectorTransicoes) {
		this.vectorTransicoes = vectorTransicoes;
	}
	
	public Vector<String> getVectorEvtNaoControl() {
		return vectorEvtNaoControl;
	}

	public void setVectorEvtNaoControl(Vector<String> vectorEvtNaoControl) {
		this.vectorEvtNaoControl = vectorEvtNaoControl;
	}

	public Vector<String> getVectorEvtControl() {
		return vectorEvtControl;
	}

	public void setVectorEvtControl(Vector<String> vectorEvtControl) {
		this.vectorEvtControl = vectorEvtControl;
	}

	/**
	 *  Código Gerado para a familia MSP430Gxx com saída Mealy
	 *  Desenvolvido por César Rafael Claure Torrico
	 * @param define
	 * @param value
	 * @return
	 */
	public void converterJavaToC() {
		setCabecalho();
		setConstantes();
		setVariaveis();
		setMapeiaEventosNaoControlaveis();
		setEventosSaida();
		setFuncoesInicializacao();
		setVariaveisGlobais();
		setMain();
		setTratamentoInterrupcao();
		setConfig_clk();
		setConfig_timer();
		setConfig_io();
		setConfig_tempo_ms();		
	}

	private int getContem(String source) {
		int posicao = 0;

		for (Transicoes t : vectorTransicoes) {
			if (t.getSource().equals(source)) {
				posicao = t.getPosicao();
			}
		}

		return posicao;
	}
	
	private int getContem2(String source) {
		int posicao = 0;

		for (Transicoes t : vectorTransicoes) {
			if (t.getSource().equals(source)) {
				posicao++;
			}
		}

		return posicao;
	}
	
	private void writeContent(String wcontent){
	
		File dir = new File("C://ConverterC");

		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		File file = new File("C://ConverterC/automato.c");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(wcontent);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setCabecalho() {

		content = "#include \"msp430.h\" \n\n";		
		writeContent(content);

	}		
	private void setConstantes() {
		
		content += "//Dados do autômato \n";
		content += "#define NTRANS " + ntrans + " \n";
		content += "#define NESTADOS " + nestados + " \n";
		content += "#define BUFFER 10 \n\n";
		
		writeContent(content);
			
	}
	
	private void setVariaveis() {

		String events = "";
		String in_states = "";
		String rfirst = "";
		String rnext = "";		
		
		String source_aux ="";
		String source_aux2 = "";
		int iteracoes = 0;
		
		for(Transicoes t : vectorTransicoes){
			if (events == "") {
				events += t.getEvent();	
			}else	{
				events += ", " + t.getEvent();
			}
			
			if (in_states == "") {
				in_states += t.getDest();	
			}else	{
				in_states += ", " + t.getDest();
			}
			
			if (source_aux == "" || source_aux != t.getSource()) {
				int posicao = getContem(t.getSource());
				if (rfirst == "") {
					rfirst += posicao;
				}	else{
					rfirst += ", " + posicao;
				}
				if (iteracoes == vectorTransicoes.size()-1) {
					rfirst += ", 0";					
				}
				source_aux = t.getSource();
			}					

			if ((getContem2(t.getSource()) > 1 && iteracoes == 0) || (source_aux2 != t.getSource())) {
				if (rnext == "") {
					rnext += "0";
				} else {
					rnext += ", 0";
				}
			} else {
				if (rnext == "") {
					rnext += getContem(t.getSource()) - 1;
				} else {
					rnext += ", " + (getContem(t.getSource()) - 1);
				}
			}			
			source_aux2 = t.getSource();			
			iteracoes++;
		}
		
		content += "const unsigned int event[NTRANS]={"+ events +"} \n";
		content += "const unsigned int in_state[NTRANS]={"+ in_states +"} \n";
		content += "const unsigned int rfirst[NESTADOS]={"+ rfirst +"} \n";
		content += "const unsigned int rnext[NTRANS]={"+ rnext +"} \n\n";
		
		writeContent(content);

	}
	
	/**
	 * Mapeamento de eventos não controlaveis como entradas 
	 * @param define
	 * @param value
	 */
	private void setMapeiaEventosNaoControlaveis() {

		int count = 0;		
		for(String s : vectorEvtNaoControl){
			content += "#define E"+count + " " + s;
			if (count == vectorEvtNaoControl.size()-1) {
				content += " \n\n";
			}else{
				content +=  " \n";
			}
			count++;
		}
		
		writeContent(content);

	}

	private void setEventosSaida() {

			content += " #define S0_ON  P2OUT |= BIT0	//Saida 0 ON  \n";
			content += " #define S0_OFF P2OUT &=~BIT0	//Saida 0 OFF \n";
			content += " #define S1_ON  P2OUT |= BIT1	//Saida 1 ON  \n";
			content += " #define S1_OFF P2OUT &=~BIT1	//Saida 1 OFF \n";
			content += " #define S2_ON  P2OUT |= BIT2	//Saida 2 ON  \n";
			content += " #define S2_OFF P2OUT &=~BIT2	//Saida 2 OFF \n";
			content += " #define S3_ON  P2OUT |= BIT3	//Saida 3 ON  \n";
			content += " #define S3_OFF P2OUT &=~BIT3	//Saida 3 OFF \n";
			content += " #define S4_ON  P2OUT |= BIT4	//Saida 4 ON  \n";
			content += " #define S4_OFF P2OUT &=~BIT4	//Saida 4 OFF \n";
			content += " #define S5_ON  P2OUT |= BIT5	//Saida 5 ON  \n";
			content += " #define S5_OFF P2OUT &=~BIT5	//Saida 5 OFF \n";
			content += " #define S6_ON  P2OUT |= BIT6	//Saida 6 ON  \n";
			content += " #define S6_OFF P2OUT &=~BIT6	//Saida 6 OFF \n";
			content += " #define S7_ON  P2OUT |= BIT7	//Saida 7 ON  \n";
			content += " #define S7_OFF P2OUT &=~BIT7	//Saida 7 OFF \n\n";				

			writeContent(content);			

		}
		
	private void setFuncoesInicializacao(){
		
		content += "void config_clk(void); \n";
		content += "void config_timer(void); \n";
		content += "void config_io(void); \n";
		content += "void tempo_ms(unsigned int); \n\n";
		
		writeContent(content);
		
	}
	
	private void setVariaveisGlobais(){
		
		content += "unsigned int buffer[BUFFER]; \n";
		content += "unsigned int n_buffer=0; \n\n";
		
		writeContent(content);	
	
	}
	
	private void setMain(){
		
		content += "void main(void) \n";
		content += "{ \n";
		
		content += "	WDTCTL = WDTPW + WDTHOLD; \n";
		content += "	config_clk(); \n";
		content += "	config_timer(); \n";
		content += "	config_io(); \n\n";
		
		content += "	unsigned int k; \n";
		content += "	int occur_event; \n";
		content += "	unsigned int current_state = 0; \n";
		content += "	int g=0; \n";
		content += "	int gerar_evento=1; \n";
		content += "	int mealy_output = 0; \n";
		content += "	_enable_interrupt(); \n\n";
		
		content += "	while(1) \n ";
		content += "	{ \n\n";
		
		content += "		if(n_buffer == 0) \n";
		content += "		{ \n";
		content += "			if(TACTL&TAIFG) \n";
		content += "			{ \n";
		content += "				gerar_evento=1; \n";
		content += "			} \n";
		content += "			if(gerar_evento==1) \n";
		content += "			{ \n";
		content += "				switch(g) \n";
		content += "				{ \n";
		
		int count = 0;
		String g= "g++";
		for(String evc : vectorEvtControl){
			
			if (vectorEvtControl.size()-1 ==count) {
				g = "g=0";
			}
			content += "					case(" + count +"): \n";
			content += "						occur_event=" + evc + "; \n";
			content += "						" + g +"; \n";
			content += "						break; \n";
			
			count++;
		}
		
		content += "				} \n";
		content += "			} \n";
		content += "		} \n";
		content += "		else \n";
		content += "		{ \n";
		content += "			occur_event = buffer[0]; \n";
		content += "			n_buffer--; \n";
		content += "			k = 0; \n";
		content += "			while(k<n_buffer) \n";
		content += "			{ \n";
		content += "				buffer[k] = buffer[k+1]; \n";
		content += "				k++; \n";
		content += "			} \n";
		content += "		} \n\n";
		
		content += "		k = rfirst[current_state]; \n";
		content += "		if(k==0) \n";
		content += "		{ \n";
		content += "			return;     //Dead Lock!!! \n";
		content += "		} \n";
		content += "		else \n";
		content += "		{ \n";		
		content += "			while(k>0) \n";
		content += "			{ \n ";
		content += "				k--; \n";
		content += "				if(event[k] == occur_event) \n";
		content += "				{ \n";
		content += "					current_state = in_state[k]; \n";
		content += "					mealy_output = 1; \n";
		content += "					break; \n";
		content += "				} \n";
		content += "				k = rnext[k]; \n";
		content += "			} \n";
		content += "		} \n";
		
		content += "		if(mealy_output) \n";
		content += "		{ \n";
		content += "			switch(occur_event) \n";
		content += "			{ \n";		
		content += "				//eventos controláveis \n";
		
		for(String ec : vectorEvtControl){			
			content += "				case(" + ec + "): \n\n";
			content += "					break; \n";						
		}
		
		for(String enc : vectorEvtNaoControl){			
			content += "				case(" + enc + "): \n\n";
			content += "					gerar_evento=1; \n";
			content += "					break; \n";						
		}
		
		content += "			}//fim switch \n";		
		content += "			mealy_output = 0; \n";
		content += "			occur_event = -1; \n";		
		content += "		}//fim if(mealy_output) \n";			
		content += "	}//fim while(1)";			
		content += "}//fim main \n\n\n";
		
	}
	
	// de acordo com o define tratamento de interrupção
	private void setTratamentoInterrupcao() {

		String BIT = "BIT";
		String EVENTO_P = "E";		
		int count = 0;
				
		content += " #pragma vector=PORT1_VECTOR \n";
		content += "__interrupt void RTI_PORT1(void) \n";
		content += "{ \n";
		
		for(String s : vectorEvtNaoControl){
						
			BIT = "BIT" + count;
			EVENTO_P = "E" + count;
			
			content += "	if(P1IFG&"+ BIT +  ") \n";
			content += "	{\n";
			content += "		P1IFG&=~"+ BIT + "; \n";
			content += "		buffer[n_buffer]="+ EVENTO_P + "; \n";
			content += "		if(n_buffer<BUFFER-1) n_buffer++; \n";
			content += "	} \n";
			
			count++;
		}
		
		content += "	P1IFG=0; \n";
		content += "}\n\n";			

		writeContent(content);

	}
	
	private void setConfig_clk() {

		content += " void config_clk(void) \n";
		content += "{ \n";
		content += "	DCOCTL = 0x00; \n";			
		content += "	__delay_cycles(100000);\n";
		content += "	DCOCTL = MOD0 + MOD1 + MOD2 + MOD3 + MOD4 + DCO0; \n";
		content += "	BCSCTL1 = XT2OFF + DIVA_0 + RSEL1 + RSEL2 + RSEL3; \n";
		content += "} \n";
		
		writeContent(content);

	}
	
	private void setConfig_timer() {

		content += " void config_timer(void) \n";
		content += "{ \n";
		content += "	TACTL = TASSEL_1 + ID_2 + MC_1; \n";
		content += "	BCSCTL3 |= LFXT1S1; \n";		
		content += "} \n";
		
		writeContent(content);

	}
	
	private void setConfig_io() {

		content += " void config_timer(void) \n";
		content += "{ \n";
		content += "	P1DIR = 0; \n";
		content += "	P1REN |= BIT0+BIT1+BIT2+BIT3+BIT4+BIT5+BIT6+BIT7; \n";
		content += "	P1OUT |= BIT0+BIT1+BIT2+BIT3+BIT4+BIT5+BIT6+BIT7; \n";
		content += "	P1IE  |= BIT0+BIT1+BIT2+BIT3+BIT4+BIT5+BIT6+BIT7; \n";
		content += "	P1IES |= BIT0+BIT1+BIT2+BIT3+BIT4+BIT5+BIT6+BIT7; \n";
		content += "	P1IFG = 0; \n";
		content += "	P2SEL &= ~(BIT6 | BIT7); \n";
		content += "	P2DIR |= BIT0+BIT1+BIT2+BIT3+BIT4+BIT5+BIT6+BIT7; \n";
		content += "	P2OUT = 0; \n";			
		content += "} \n";
		
		writeContent(content);		

	}
	
	private void setConfig_tempo_ms() {

		content += " void tempo_ms(unsigned int tempo) \n";
		content += "{ \n";
		content += "	TACTL|= TACLR; \n";
		content += "	TACCR0 =3*tempo; \n";
		content += "	TACTL &= ~TAIFG; \n";					
		content += "} \n";
		
		writeContent(content);

	}

}
