package com.figaro.model;

import static com.figaro.util.Constants.HORARIO_CORRIDO;
import static com.figaro.util.Constants.HORARIO_DESDE_MAÑANA;
import static com.figaro.util.Constants.HORARIO_DESDE_MAÑANA_DEFAULT;
import static com.figaro.util.Constants.HORARIO_DESDE_TARDE;
import static com.figaro.util.Constants.HORARIO_DESDE_TARDE_DEFAULT;
import static com.figaro.util.Constants.HORARIO_HASTA_MAÑANA;
import static com.figaro.util.Constants.HORARIO_HASTA_MAÑANA_DEFAULT;
import static com.figaro.util.Constants.HORARIO_HASTA_TARDE;
import static com.figaro.util.Constants.HORARIO_RANGO_DEFAULT;
import static com.figaro.util.Constants.HORARIO_RANGO_USUARIO;

import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;



public class Horario {
	
	private Boolean corrido;
	private String desde;
	private String hasta;
	private String desdeTarde;
	private String hastaTarde;
	private List<String> rango;
	private List<String> rangoUsuario;

	public Horario() {}
	
	public Horario(Preferences preferencias) {
		this.corrido =  preferencias.getBoolean(HORARIO_CORRIDO, false);
		this.desde = preferencias.get(HORARIO_DESDE_MAÑANA, HORARIO_DESDE_MAÑANA_DEFAULT);
		this.hasta =  preferencias.get(HORARIO_HASTA_MAÑANA, HORARIO_HASTA_MAÑANA_DEFAULT)  ;
		this.desdeTarde = preferencias.get(HORARIO_DESDE_TARDE  , HORARIO_DESDE_TARDE_DEFAULT);
		this.hastaTarde = preferencias.get( HORARIO_HASTA_TARDE   , HORARIO_HASTA_TARDE);
		this.rango = Arrays.asList(HORARIO_RANGO_DEFAULT.split(" "));
		this.rangoUsuario = Arrays.asList(preferencias.get(HORARIO_RANGO_USUARIO , HORARIO_RANGO_DEFAULT).split(" "));		
	}

	public Boolean getCorrido() {
		return corrido;
	}
	public void setCorrido(Boolean corrido) {
		this.corrido = corrido;
	}
	public String getDesde() {
		return desde;
	}
	public void setDesde(String desde) {
		this.desde = desde;
	}
	public String getHasta() {
		return hasta;
	}
	public void setHasta(String hasta) {
		this.hasta = hasta;
	}
	public String getDesdeTarde() {
		return desdeTarde;
	}
	public void setDesdeTarde(String desdeTarde) {
		this.desdeTarde = desdeTarde;
	}
	public String getHastaTarde() {
		return hastaTarde;
	}
	public void setHastaTarde(String hastaTarde) {
		this.hastaTarde = hastaTarde;
	}
	public List<String> getRango() {
		return rango;
	}
	public void setRango(List<String> rango) {
		this.rango = rango;
	}

	public List<String> getRangoUsuario() {
		return rangoUsuario;
	}

	public void setRangoUsuario(List<String> rangoUsuario) {
		this.rangoUsuario = rangoUsuario;
	}
}
