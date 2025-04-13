package br.com.portfoliopelusci.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfoliopelusci.model.ContatoRequest;
import br.com.portfoliopelusci.service.HubspotService;

@RestController
@RequestMapping("/hubspot")
public class HubspotController {

    private final HubspotService hubspotService;

    public HubspotController(HubspotService hubspotService) {
        this.hubspotService = hubspotService;
    }

    @PostMapping("/contatos")
    public ResponseEntity<String> criarContato(@RequestBody ContatoRequest request,
            @RequestHeader("Authorization") String authorizationHeader) {
    	String accessToken = authorizationHeader.replace("Bearer ", "");
        hubspotService.criarContato(request,accessToken);
        return ResponseEntity.ok("Contato criado com sucesso!");
    }
    
}
