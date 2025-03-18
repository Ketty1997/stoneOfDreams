package com.example.demo.services;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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
            String mess = jsonResponse.getJSONObject("data").getString("horoscope_data");
            // System.out.println("messaggio oroscopo: " + mess);


            return mess;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore nel recupero dell'oroscopo -> propabilmente non hai impostato il segnozodiacale in INGLESE sul db";
        }
    }


	
    public String traduciFrase(String frase) {
        try {
            // Codifica corretta della query e dei parametri
            String encodedPhrase = URLEncoder.encode(frase, StandardCharsets.UTF_8);
            String encodedLangPair = URLEncoder.encode("en|it", StandardCharsets.UTF_8);
            
            // URL dell'API con i parametri codificati
            String url = "https://api.mymemory.translated.net/get?q=" + encodedPhrase + "&langpair=" + encodedLangPair;

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

            // Controllo della risposta per evitare errori
            if (jsonResponse.has("responseData") && !jsonResponse.isNull("responseData")) {
                
                String translatedText = jsonResponse.getJSONObject("responseData").getString("translatedText");
          
                return translatedText;
            } else {
                return "Errore nella risposta dell'API";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore nel recupero della frase tradotta";
        }
    }


}

