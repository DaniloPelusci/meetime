package br.com.portfoliopelusci.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.portfoliopelusci.dto.OAuthDTO;

@Service
public class OAuthService {



    public String generateAuthorizationUrl(OAuthDTO outh) {
        return UriComponentsBuilder
                .fromHttpUrl("https://app.hubspot.com/oauth/authorize")
                .queryParam("client_id", outh.getClientId())
                .queryParam("redirect_uri", outh.getRedirectUri())
                .queryParam("scope", outh.getScope())
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    public Map<String, Object> exchangeCodeForToken(OAuthDTO outh) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", outh.getClientId());
        form.add("client_secret", outh.getClientSecret());
        form.add("redirect_uri", outh.getRedirectUri());
        form.add("code", outh.getCode());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.hubapi.com/oauth/v1/token",
                request,
                Map.class
        );


        return response.getBody();
    }
}