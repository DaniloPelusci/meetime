package br.com.portfoliopelusci.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfoliopelusci.service.OAuthService;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/authorize-url")
    public ResponseEntity<Map<String, String>> getAuthorizationUrl() {
        String url = oAuthService.generateAuthorizationUrl();
        Map<String, String> response = new HashMap<>();
        response.put("authorization_url", url);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleCallback(@RequestParam("code") String code) {
        Map<String, Object> tokenResponse = oAuthService.exchangeCodeForToken(code);
        return ResponseEntity.ok(tokenResponse);
    }
}