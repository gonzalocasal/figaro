package com.figaro.exception;

import com.figaro.model.Turno;

public class TurnoOcupadoException extends RuntimeException  {

	public TurnoOcupadoException(Turno turno) {
		super(turno.toString());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
