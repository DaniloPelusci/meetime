package br.com.portfoliopelusci.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.portfoliopelusci.model.ContatoRequest;

@Service
public class HubspotService {

    
    public void criarContato(ContatoRequest contato, String accessToken) {
        String url = "https://api.hubapi.com/crm/v3/objects/contacts";

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

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, request, String.class);
    }
}
