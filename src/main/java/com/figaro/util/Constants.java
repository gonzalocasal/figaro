package com.figaro.util;

public class Constants{

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT ="yyyy-MM-dd HH:mm";
	public static final String FRONT_DATE_FORMAT = "dd/MM/yyyy";
	
	
	public static final String CATEGORIA_TURNOS = "Turnos";
	public static final String CATEGORIA_PELUQUERO = "Peluqueros";
	public static final String CATEGORIA_VENTAS = "Ventas";

	public static final String TIPO_PAGO_DEBITO  ="debito";
	public static final String TIPO_PAGO_CREDITO ="credito";
	public static final String TIPO_PAGO_CONTADO ="contado";
	
	public static final String MSG_DUPLICADO = "Ya existe un elemento con estos datos";
	public static final String MSG_TURNO_OCUPADO_CLIENTE   = "Ya existe un turno ocupado por ese cliente";
	public static final String MSG_TURNO_OCUPADO_PELUQUERO = "Ya existe un turno ocupado por ese peluquero";
	public static final String MSG_HORARIO_INVALIDO = "El horario seleccionado es invalido";
    public static final String MSG_DESCUENTO_INVALIDO = "El descuento no puede ser mayor al monto total";

}
