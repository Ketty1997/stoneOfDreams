package com.example.demo.services;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HoroscopeService {
	public String getHoroscope(String sign) {
		// URL dell'API per ottenere l'oroscopo giornaliero
        String url = "https://zodiacal.herokuapp.com/api/horoscope/" + sign;

        // Creo un oggetto RestTemplate per effettuare la chiamata HTTP
        RestTemplate restTemplate = new RestTemplate();

        // Impostiamo l'header per accettare la risposta come JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Creo la richiesta HTTP
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Effettuo la richiesta HTTP GET e ottengo la risposta come stringa
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            // Controllo se la risposta è valida e restituisco il messaggio dell'oroscopo
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("horoscope");
            } else {
                return "Impossibile ottenere l'oroscopo al momento.";
            }
        } catch (Exception e) {
            return "Errore nel recupero dell'oroscopo: " + e.getMessage();
        }
    }
}

