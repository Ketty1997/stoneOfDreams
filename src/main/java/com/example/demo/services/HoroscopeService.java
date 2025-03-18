package com.example.demo.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class HoroscopeService {
	public String getHoroscope(String sign) {
		// // URL dell'API per ottenere l'oroscopo giornaliero
        String url = "https://horoscope-app-api.vercel.app/api/v1/get-horoscope/daily?sign=" + sign + "&day=TODAY";

        
        try {
            // Creazione dell'HttpClient
            HttpClient client = HttpClient.newHttpClient();
            
            // Creazione della richiesta GET
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
            
            // Invio della richiesta e ricezione della risposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parsing della risposta JSON
            JSONObject jsonResponse = new JSONObject(response.body());
            
            // Estrazione del campo "horoscope_data" da "data"
            return jsonResponse.getJSONObject("data").getString("horoscope_data");
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore nel recupero dell'oroscopo -> propabilmente non hai impostato il segnozodiacale in INGLESE sul db";
        }
    }
}

