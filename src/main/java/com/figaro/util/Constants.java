	package com.figaro.util;

public class Constants{

	public static final String TIME_FORMAT ="HH:mm";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT ="yyyy-MM-dd HH:mm";
	public static final String FRONT_DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String EMAILS_TAG_TURNOS = "%turnos%";
	public static final String EMAILS_TAG_NAME = "%name%";
	public static final String EMAILS_TURNO_FORMAT_EMPLEADO = "<p>%s %s a %s</p>";
	public static final String EMAILS_TURNO_FORMAT_CLIENTE = "<p>%s %s con %s</p>";
	public static final String EMAILS_SENDGRID_API_KEY = "SENDGRID_API_KEY";
	public static final String EMAILS_SENDGRID_MAIL_SEND = "mail/send";
	public static final String EMAILS_CLIENTES_ASUNTO  = "Ten\u00e9s un turno para hoy";
	public static final String EMAILS_CLIENTES_TEMPLATE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><htmlxmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/><title>Figaro Mail</title><style type=\"text/css\">@media screen and (max-width: 600px){table[class=\"container\"]{width: 95% !important;}}#outlook a{padding:0;}body{width:100% !important; -webkit-text-size-adjust:100%; -ms-text-size-adjust:100%; margin:0; padding:0;}.ExternalClass{width:100%;}.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{line-height: 100%;}#backgroundTable{margin:0; padding:0; width:100% !important; line-height: 100% !important;}img{outline:none; text-decoration:none; -ms-interpolation-mode: bicubic;}a img{border:none;}.image_fix{display:block;}p{margin: 1em 0;}h1, h2, h3, h4, h5, h6{color: black !important;}h1 a, h2 a, h3 a, h4 a, h5 a, h6 a{color: blue !important;}h1 a:active, h2 a:active, h3 a:active, h4 a:active, h5 a:active, h6 a:active{color: red !important;}h1 a:visited, h2 a:visited, h3 a:visited, h4 a:visited, h5 a:visited, h6 a:visited{color: purple !important;}table td{border-collapse: collapse;}table{border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;}a{color: #000;}@media only screen and (max-device-width: 480px){a[href^=\"tel\"], a[href^=\"sms\"]{text-decoration: none;color: black; /* or whatever your want */pointer-events: none;cursor: default;}.mobile_link a[href^=\"tel\"], .mobile_link a[href^=\"sms\"]{text-decoration: default;color: orange !important; /* or whatever your want */pointer-events: auto;cursor: default;}}@media only screen and (min-device-width: 768px) and (max-device-width: 1024px){a[href^=\"tel\"], a[href^=\"sms\"]{text-decoration: none;color: blue; /* or whatever your want */pointer-events: none;cursor: default;}.mobile_link a[href^=\"tel\"], .mobile_link a[href^=\"sms\"]{text-decoration: default;color: orange !important;pointer-events: auto;cursor: default;}}@media only screen and (-webkit-min-device-pixel-ratio: 2){/* Put your iPhone 4g styles in here */}@media only screen and (-webkit-device-pixel-ratio:.75){/* Put CSS for low density (ldpi) Android layouts in here */}@media only screen and (-webkit-device-pixel-ratio:1){/* Put CSS for medium density (mdpi) Android layouts in here */}@media only screen and (-webkit-device-pixel-ratio:1.5){/* Put CSS for high density (hdpi) Android layouts in here */}/* end Android targeting */h2{color:#181818;font-family:Helvetica, Arial, sans-serif;font-size:22px;line-height: 22px;font-weight: normal;}a.link1{}a.link2{color:#fff;text-decoration:none;font-family:Helvetica, Arial, sans-serif;font-size:16px;color:#fff;border-radius:4px;}p{color:#555;font-family:Helvetica, Arial, sans-serif;font-size:16px;line-height:160%;}</style><script type=\"colorScheme\" class=\"swatch active\">{\"name\":\"Default\", \"bgBody\":\"ffffff\", \"link\":\"fff\", \"color\":\"555555\", \"bgItem\":\"ffffff\", \"title\":\"181818\"}</script></head><body ><table cellpadding=\"0\" width=\"100%\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" class='bgBody'><tr><td><table cellpadding=\"0\" width=\"620\" class=\"container\" align=\"center\" cellspacing=\"0\" border=\"0\"><tr><td><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" width=\"600\" class=\"container\"><tr><td class='movableContentContainer bgItem'><div class='movableContent'><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" width=\"600\" class=\"container\"><tr><td width=\"100%\" colspan=\"3\" align=\"center\" style=\"padding-bottom:10px;padding-top:25px;\"><div class=\"contentEditableContainer contentTextEditable\"><div class=\"contentEditable\" align='left' ><h2 >Hola %name%, no te olvides de tu turno:</h2></div></div></td></tr><tr><td width=\"100\">&nbsp;</td><td width=\"100%\" align=\"center\"><div class=\"contentEditableContainer contentTextEditable\"><div class=\"contentEditable\" align='left' > %turnos% </div></div></td><td width=\"100\">&nbsp;</td></tr></table></div><div class='movableContent'><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" width=\"600\" class=\"container\"><tr><td width=\"100%\" colspan=\"2\" style=\"padding-top:65px;\"><hr style=\"height:1px;border:none;color:#333;background-color:#ddd;\"/></td></tr><tr><td width=\"60%\" height=\"70\" valign=\"middle\" style=\"padding-bottom:20px;\"><div class=\"contentEditableContainer contentTextEditable\"><div class=\"contentEditable\" align='left' ><span style=\"font-size:13px;color:#555;font-family:Helvetica, Arial, sans-serif;line-height:200%;\">Mensaje env&iacute;ado a trav&eacute;s del sistema Figaro, </span><span style=\"font-size:13px;color:#555;font-family:Helvetica, Arial, sans-serif;line-height:200%;\">visite <a target='_blank' href=\"https://figaro.com.ar\" style=\"text-decoration:none;color:#555\">figaro.com.ar</a> para m&aacute;s informaci&oacute;n.</span></div></div></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table></body></html>";
	public static final String EMAILS_EMPLEADOS_ASUNTO = "Tus turnos para hoy";
	public static final String EMAILS_EMPLEADOS_TEMPLATE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><htmlxmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/><title>Figaro Mail</title><style type=\"text/css\">@media screen and (max-width: 600px){table[class=\"container\"]{width: 95% !important;}}#outlook a{padding:0;}body{width:100% !important; -webkit-text-size-adjust:100%; -ms-text-size-adjust:100%; margin:0; padding:0;}.ExternalClass{width:100%;}.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{line-height: 100%;}#backgroundTable{margin:0; padding:0; width:100% !important; line-height: 100% !important;}img{outline:none; text-decoration:none; -ms-interpolation-mode: bicubic;}a img{border:none;}.image_fix{display:block;}p{margin: 1em 0;}h1, h2, h3, h4, h5, h6{color: black !important;}h1 a, h2 a, h3 a, h4 a, h5 a, h6 a{color: blue !important;}h1 a:active, h2 a:active, h3 a:active, h4 a:active, h5 a:active, h6 a:active{color: red !important;}h1 a:visited, h2 a:visited, h3 a:visited, h4 a:visited, h5 a:visited, h6 a:visited{color: purple !important;}table td{border-collapse: collapse;}table{border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;}a{color: #000;}@media only screen and (max-device-width: 480px){a[href^=\"tel\"], a[href^=\"sms\"]{text-decoration: none;color: black; /* or whatever your want */pointer-events: none;cursor: default;}.mobile_link a[href^=\"tel\"], .mobile_link a[href^=\"sms\"]{text-decoration: default;color: orange !important; /* or whatever your want */pointer-events: auto;cursor: default;}}@media only screen and (min-device-width: 768px) and (max-device-width: 1024px){a[href^=\"tel\"], a[href^=\"sms\"]{text-decoration: none;color: blue; /* or whatever your want */pointer-events: none;cursor: default;}.mobile_link a[href^=\"tel\"], .mobile_link a[href^=\"sms\"]{text-decoration: default;color: orange !important;pointer-events: auto;cursor: default;}}@media only screen and (-webkit-min-device-pixel-ratio: 2){/* Put your iPhone 4g styles in here */}@media only screen and (-webkit-device-pixel-ratio:.75){/* Put CSS for low density (ldpi) Android layouts in here */}@media only screen and (-webkit-device-pixel-ratio:1){/* Put CSS for medium density (mdpi) Android layouts in here */}@media only screen and (-webkit-device-pixel-ratio:1.5){/* Put CSS for high density (hdpi) Android layouts in here */}/* end Android targeting */h2{color:#181818;font-family:Helvetica, Arial, sans-serif;font-size:22px;line-height: 22px;font-weight: normal;}a.link1{}a.link2{color:#fff;text-decoration:none;font-family:Helvetica, Arial, sans-serif;font-size:16px;color:#fff;border-radius:4px;}p{color:#555;font-family:Helvetica, Arial, sans-serif;font-size:16px;line-height:160%;}</style><script type=\"colorScheme\" class=\"swatch active\">{\"name\":\"Default\", \"bgBody\":\"ffffff\", \"link\":\"fff\", \"color\":\"555555\", \"bgItem\":\"ffffff\", \"title\":\"181818\"}</script></head><body ><table cellpadding=\"0\" width=\"100%\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" class='bgBody'><tr><td><table cellpadding=\"0\" width=\"620\" class=\"container\" align=\"center\" cellspacing=\"0\" border=\"0\"><tr><td><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" width=\"600\" class=\"container\"><tr><td class='movableContentContainer bgItem'><div class='movableContent'><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" width=\"600\" class=\"container\"><tr><td width=\"100%\" colspan=\"3\" align=\"left\" style=\"padding-bottom:10px;padding-top:25px;\"><div class=\"contentEditableContainer contentTextEditable\"><div class=\"contentEditable\" align='left' ><h2 >Hola %name%, estos son tus turnos para hoy:</h2></div></div></td></tr><tr><td width=\"100\">&nbsp;</td><td width=\"100%\" align=\"center\"><div class=\"contentEditableContainer contentTextEditable\"><div class=\"contentEditable\" align='left' > %turnos% </div></div></td><td width=\"100\">&nbsp;</td></tr></table></div><div class='movableContent'><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" width=\"600\" class=\"container\"><tr><td width=\"100%\" colspan=\"2\" style=\"padding-top:65px;\"><hr style=\"height:1px;border:none;color:#333;background-color:#ddd;\"/></td></tr><tr><td width=\"60%\" height=\"70\" valign=\"middle\" style=\"padding-bottom:20px;\"><div class=\"contentEditableContainer contentTextEditable\"><div class=\"contentEditable\" align='left' ><span style=\"font-size:13px;color:#555;font-family:Helvetica, Arial, sans-serif;line-height:200%;\">Mensaje env&iacute;ado a trav&eacute;s del sistema Figaro, </span><span style=\"font-size:13px;color:#555;font-family:Helvetica, Arial, sans-serif;line-height:200%;\">visite <a target='_blank' href=\"https://figaro.com.ar\" style=\"text-decoration:none;color:#555\">figaro.com.ar</a> para m&aacute;s informaci&oacute;n.</span></div></div></td></tr></table></div></td></tr></table></td></tr></table></td></tr></table></body></html>";
	public static final String EMAILS_FROM = "Figaro@figaro.com.ar";
	public static final String EMAILS_TYPE = "text/html";
	
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
