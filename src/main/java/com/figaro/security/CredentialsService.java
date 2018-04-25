package com.figaro.security;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.figaro.service.ConfiguracionService;

@Service
public class CredentialsService {
	
	final static Logger LOGGER = Logger.getLogger(ConfiguracionService.class);
	
	public String updatePassword (String pass) {
		LOGGER.info("actualizando password");
		
        String appName = System.getenv("FIGARO_APP_NAME");
	    String apikey  = System.getenv("FIGARO_API_KEY");
        String url = String.format("https://api.heroku.com/apps/%s/config-vars", appName) ;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.heroku+json; version=3");
        headers.add("Authorization","Bearer " + apikey);
        
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();     
        body.add("FIGARO_PASS", "pass");
        
        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
        return restTemplate.patchForObject(url, null,String.class);
	  
	}
	
}
