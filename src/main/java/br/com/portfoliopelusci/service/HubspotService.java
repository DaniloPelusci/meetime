package br.com.portfoliopelusci.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.portfoliopelusci.exceptions.HubspotApiException;
import br.com.portfoliopelusci.model.ContatoRequest;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class HubspotService {

	@Retry(name = "hubspotApi", fallbackMethod = "fallbackContato")
    public void criarContato(ContatoRequest contato, String accessToken) {
        String url = "https://api.hubapi.com/crm/v3/objects/contacts";
        System.out.println("Tentando criar contato no HubSpot...");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> properties = new HashMap<>();
        properties.put("email", contato.getEmail());
        properties.put("firstname", contato.getFirstname());
        properties.put("lastname", contato.getLastname());
        properties.put("phone", contato.getPhone());

        Map<String, Object> body = new HashMap<>();
        body.put("properties", properties);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new HubspotApiException("Erro ao criar contato: " + ex.getResponseBodyAsString(), ex.getRawStatusCode());
        } catch (Exception ex) {
            throw new HubspotApiException("Erro inesperado ao criar contato: " + ex.getMessage(), 500);
        }
        
        
    }
	public void fallbackContato(ContatoRequest contato, String accessToken, Throwable ex) {
	    System.err.println(" Fallback acionado após tentativas: " + ex.getMessage());
	    throw new HubspotApiException("Erro persistente ao criar contato após retry", 429);
	}
}
