package com.figaro.exception;

import static com.figaro.util.Constants.MSG_DESCUENTO_INVALIDO;
import static com.figaro.util.Constants.MSG_DUPLICADO;
import static com.figaro.util.Constants.MSG_HORARIO_INVALIDO;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice  
@RestController  
public class GlobalExceptionHandler {  
  
	final static Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);
	
    

	@ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    public ApiError handleException(Exception e){
		LOGGER.error(MSG_DUPLICADO + e.getMessage());
    	return new ApiError(HttpStatus.BAD_REQUEST, MSG_DUPLICADO);
    }  
	
	@ExceptionHandler(value = TurnoOcupadoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    public ApiError turnoOcupado(Exception e){
		LOGGER.error( e.getMessage());
    	return new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

	@ExceptionHandler(value = TurnoOcupadoEmpleadoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)  
	public ApiError turnoEmpleadoOcupado(Exception e){
		LOGGER.warn( e.getMessage());
		return new ApiError(HttpStatus.PRECONDITION_FAILED, e.getMessage());
	}
	
	
	@ExceptionHandler(value = HorarioInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    public ApiError horarioInvalido(Exception e){
		LOGGER.error(MSG_HORARIO_INVALIDO + e.getMessage());
    	return new ApiError(HttpStatus.BAD_REQUEST, MSG_HORARIO_INVALIDO);
    }
	
  
	@ExceptionHandler(value = DescuentoInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    public ApiError descuentoInvalido(Exception e){
		LOGGER.error(MSG_DESCUENTO_INVALIDO + e.getMessage());
    	return new ApiError(HttpStatus.BAD_REQUEST, MSG_DESCUENTO_INVALIDO);
    }
	
	
}  