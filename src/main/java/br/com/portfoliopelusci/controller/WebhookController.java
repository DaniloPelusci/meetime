package br.com.portfoliopelusci.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfoliopelusci.model.WebhookEvent;

@RestController
@RequestMapping("/webhook/hubspot")
public class WebhookController {

    @PostMapping("/contact")
    public ResponseEntity<Void> receiveContactWebhook(@RequestBody List<WebhookEvent> events) {
        for (WebhookEvent event : events) {
            if ("contact.creation".equals(event.getSubscriptionType())) {
                System.out.println("Novo contato criado com ID: " + event.getObjectId());

            }
        }

        return ResponseEntity.ok().build();
    }
}