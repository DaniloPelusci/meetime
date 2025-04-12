package br.com.portfoliopelusci.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfoliopelusci.dto.OAuthDTO;
import br.com.portfoliopelusci.service.OAuthService;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/authorize-url")
    public ResponseEntity<Map<String, String>> getAuthorizationUrl(
            @RequestParam String clientId,
            @RequestParam String redirectUri,
            @RequestParam String scope) {

    	OAuthDTO authDTO = new OAuthDTO();
    	authDTO.setClientId(clientId);
    	authDTO.setRedirectUri(redirectUri);
    	authDTO.setScope(scope);
        String url = oAuthService.generateAuthorizationUrl(authDTO);
        Map<String, String> response = new HashMap<>();
        response.put("authorization_url", url);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleCallback(@RequestBody OAuthDTO authDTO) {
        Map<String, Object> tokenResponse = oAuthService.exchangeCodeForToken(authDTO);
        return ResponseEntity.ok(tokenResponse);
    }
}