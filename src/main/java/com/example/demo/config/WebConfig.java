package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(@SuppressWarnings("null") InterceptorRegistry registry) {
        // Registra l'interceptor e specifica le rotte su cui applicarlo
        /*
        |-registry.addInterceptor(new AuthInterceptor()): -|
        Un'interceptor è un componente che intercetta le richieste HTTP prima che raggiungano il controller e può 
        eseguire operazioni come il controllo dell'autenticazione, la validazione dei parametri, il logging, ecc.
        -AuthInterceptor è la classe che io ho definito e che implementa l'interfaccia HandlerInterceptor.
        Una volta registrato, questo interceptor sarà in grado di intercettare tutte le richieste HTTP che corrispondono alle rotte 
        specificate e eseguire la logica definita nei suoi metodi (preHandle, postHandle, afterCompletion).

        |->addPathPatterns("/**"):
        Questo metodo indica a Spring su quali percorsi l'interceptor deve essere applicato.
        "/**": è un'espressione che indica tutte le URL dell'applicazione, quindi l'interceptor verrà applicato 
        a tutte le richieste indirizzate all'applicazione.
        
        |->excludePathPatterns("/", "/formlogin", "/static/**"):
        Questo metodo definisce una serie di percorsi che devono essere esclusi dall'applicazione dell'interceptor.
        "/": Esclude la rotta principale (homepage) dall'intercettazione.
        "/formlogin": Esclude la rotta per la pagina di login dall'intercettazione. Questo è importante perché se l'interceptor blocca 
        le richieste non autenticate, dovresti permettere agli utenti non autenticati di accedere alla pagina di login.
        "/static/**": Esclude tutte le risorse statiche (ad esempio file CSS, JavaScript, immagini) dalla cartella static. Il pattern 
        "/static/**" indica che tutto ciò che è all'interno della cartella static (e delle sue sottocartelle) non deve essere intercettato.

        !!Se rimuovi "/login" dalla lista degli excludePathPatterns, l'interceptor AuthInterceptor verrà applicato 
        anche alle richieste indirizzate all'endpoint /login.

        Possibili conseguenze:
        Richiesta di autenticazione sull'endpoint di login: Se l'interceptor AuthInterceptor è progettato per 
        verificare se l'utente è autenticato prima di permettere l'accesso a determinate risorse, potrebbe bloccare 
        l'accesso all'endpoint /login se l'utente non è già autenticato. Questo creerebbe un problema di tipo "chicken 
        and egg", in cui un utente non autenticato non potrebbe accedere alla pagina di login per autenticarsi.

        Esecuzione di logiche aggiuntive: Se l'interceptor AuthInterceptor esegue altre logiche, come il logging delle 
        richieste o la gestione di specifiche intestazioni HTTP, queste logiche verranno eseguite anche per l'endpoint 
        /login. Questo potrebbe essere appropriato o meno, a seconda delle esigenze dell'applicazione.
        */
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**")
                                                                                            //ignoriamo tutto ciò che è in static, anche le immagini altrimenti non si 
                                                                                            //vedrebbero dopo aver fatto logout
                .excludePathPatterns("","/", "/formlogin","/login", "/signup", "/signupProcess", "/static/**", "/images/**", "/css/**");

    }

    
}
