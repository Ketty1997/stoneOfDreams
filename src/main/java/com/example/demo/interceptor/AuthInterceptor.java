package com.example.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// è una classe di spring che intercetta tutte le richieste HTTP, come un filtro e controlla giu nel metodo
//che se il valore user nella session è null significa che l'utente non è autenticato
@Component
public class AuthInterceptor implements HandlerInterceptor{

    /* usare un HandlerInterceptor per controllare la sessione prima che qualsiasi richiesta venga gestita dai controller. 
    * Questo ci permette di centralizzare la logica di controllo e applicarla su tutte le rotte (o su un insieme selezionato).

    * Poi, registra l'interceptor nella configurazione di Spring --> in config > WebConfig.java
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        
        if (session.getAttribute("user") == null) {
            // L'utente non è autenticato, lo reindirizziamo alla pagina di login
            response.sendRedirect("/formlogin");
            return false;
        } 
        // L'autenticazione è valida, l'utente può accedere alla risorsa richiesta e quindi lo rimanda al controller
        return true;
    }
    
}
