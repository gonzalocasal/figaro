package com.figaro.security;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.figaro.service.ConfiguracionService;
import com.mysql.fabric.Response;

@Service
public class CredentialsService {
	
	final static Logger LOGGER = Logger.getLogger(ConfiguracionService.class);
	
	public String updatePassword (String pass) {
		LOGGER.info("Actualizando password");
		
        String appName = System.getenv("FIGARO_APP_NAME");
	    String apikey  = System.getenv("FIGARO_API_KEY");
	    String url = String.format("https://api.heroku.com/apps/%s/config-vars", appName) ;

        RestTemplate restTemplate = new RestTemplate();
        
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10000);
		requestFactory.setReadTimeout(10000);
		restTemplate.setRequestFactory(requestFactory);
		
        
        
		
		
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.heroku+json; version=3");
        headers.add("Authorization","Bearer " + apikey);
        
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();     
        body.add("FIGARO_PASS", pass);
        
        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, Response.class);
        
        return null;
	  
	}
	
}
