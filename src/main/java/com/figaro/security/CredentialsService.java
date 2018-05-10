package com.figaro.security;

import static com.figaro.util.Constants.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.figaro.model.Credential;
import com.figaro.service.ConfiguracionService;

@Service
public class CredentialsService {
	
	final static Logger LOGGER = Logger.getLogger(ConfiguracionService.class);

	public String getEmail() {
		return System.getenv(FIGARO_API_KEY);
	}
	
	public void updateEmail(String email) {
		LOGGER.info("Actualizando email");
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();     
        body.add(FIGARO_EMAIL, email);
        String url = String.format(FIGADO_API_URL, System.getenv(FIGARO_APP_NAME)) ;
        RestTemplate restTemplate = generateRestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(body, generateHeaders(System.getenv(FIGARO_API_KEY)));
        restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
	}
	
	public void updatePassword (Credential credential) {
		LOGGER.info("Actualizando password");
		validate(credential);
	    String url = String.format(FIGADO_API_URL, System.getenv(FIGARO_APP_NAME)) ;
        RestTemplate restTemplate = generateRestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(generateBody(credential.getPass()), generateHeaders(System.getenv(FIGARO_API_KEY)));
        restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
	}

	private void validate(Credential credential) {
		if (!credential.getPass().equals(credential.getRepass()) || null == System.getenv(FIGARO_PASS))
			throw new RuntimeException(MSG_PASS_NO_COINCIDEN);
	}

	private RestTemplate generateRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10000);
		requestFactory.setReadTimeout(10000);
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}

	private MultiValueMap<String, String> generateBody(String pass) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();     
        body.add(FIGARO_PASS, pass);
		return body;
	}

	private MultiValueMap<String, String> generateHeaders(String apikey) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();     
        headers.add(FIGARO_API_ACCEPT, FIGARO_API_CONTENT_TYPE);
        headers.add(FIGARO_API_AUTHORIZATION, String.format(FIGARO_API_BEARER, apikey));
		return headers;
	}

}
