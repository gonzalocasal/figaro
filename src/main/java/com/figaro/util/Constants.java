	package com.figaro.util;

public class Constants{

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT ="yyyy-MM-dd HH:mm";
	public static final String FRONT_DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String CLIENTE_DESCONOCIDO_NOMBRE = "Cliente";
	public static final String CLIENTE_DESCONOCIDO_APELLIDO = "Desconocido";
	
	public static final String SIN_CIUDAD = "Sin Ciudad";
	
	public static final String CATEGORIA_TURNOS = "Turnos";
	public static final String CATEGORIA_EMPLEADOS = "Empleados";
	public static final String CATEGORIA_VENTAS = "Ventas";

	public static final String TIPO_PAGO_DEBITO  ="debito";
	public static final String TIPO_PAGO_CREDITO ="credito";
	public static final String TIPO_PAGO_CONTADO ="contado";
	
	public static final int    HORARIO_INTERVALO = 15;
	public static final String HORARIO_FORMATO = "HH:mm";
	public static final String HORARIO_DESDE_DEFAULT = "08:00";
	public static final String HORARIO_HASTA_DEFAULT = "12:00";
	public static final String HORARIO_DESDE_TARDE_DEFAULT = "16:00";
	public static final String HORARIO_HASTA_TARDE_DEFAULT = "21:00";	
	
	public static final String HORARIO_CORRIDO = "CORRIDO";
		
	public static final String HORARIO_RANGO_DEFAULT = "08:00 08:15 08:30 08:45 09:00 09:15 09:30 09:45 10:00 10:15 10:30 10:45 11:00 11:15 11:30 11:45 12:00 12:15 12:30 12:45 13:00 13:15 13:30 13:45 14:00 14:15 14:30 14:45 15:00 15:15 15:30 15:45 16:00 16:15 16:30 16:45 17:00 17:15 17:30 17:45 18:00 18:15 18:30 18:45 19:00 19:15 19:30 19:45 20:00 20:15 20:30 20:45 21:00";
	
	
	public static final String MSG_DUPLICADO = "Ya existe un elemento con estos datos";
	public static final String MSG_TURNO_OCUPADO_CLIENTE   = "Error: el cliente ya tiene un turno reservado en ese horario";
	public static final String MSG_TURNO_OCUPADO_EMPLEADO = "Advertencia: el empleado ya tiene un turno reservado en ese horario";
	public static final String MSG_HORARIO_INVALIDO =   "Error: El horario seleccionado es inválido";
    public static final String MSG_DESCUENTO_INVALIDO = "Error: El descuento no puede ser mayor al monto total";

}
