package aps.unip.protocolo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import aps.unip.enums.Requisicao;
import aps.unip.enums.Status;

/*
 * Essa classe representa a mensagem que transita entre o cliente e o servidor.
 */
public class Mensagem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Requisicao requisicao;
	private Status status;
	private Map<String, Object> parametros = new HashMap<String, Object>();
	
	
	public Mensagem() {
		
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public void setParametros(String chave, Object valor){
		parametros.put(chave, valor);
	}
	
	public Object getParametro(String chave) {
		return parametros.get(chave);
		
	}
	
	public void setMap(Map<String, Object> map) {
		this.parametros = map;
	}
	
	public Map<String, Object> getMap(){
		return this.parametros;
	}

	public Requisicao getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}
}
