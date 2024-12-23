package executor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Diese Konfigurationsklasse stellt eine CORS-Konfiguration für die Spring-Anwendung bereit.
 * 
 * CORS (Cross-Origin Resource Sharing) ist eine Sicherheitsfunktion in Webbrowsern, die es einem Webbrowser
 * ermöglicht, Ressourcen von einer Domain auf einer anderen Domain anzufordern. Diese Klasse stellt sicher, dass
 * API-Endpunkte nur von bestimmten Origin-Adressen zugänglich sind und bestimmte HTTP-Methoden und Header erlaubt sind.
 * 
 * In dieser Konfiguration wird die CORS-Policy so festgelegt, dass API-Endpunkte unter dem Pfad "/api/**" nur
 * von der URL "http://127.0.0.3:5500" aus erreichbar sind. Es werden nur die HTTP-Methoden "GET" und "POST"
 * zugelassen und beliebige Header werden akzeptiert.
 * 
 * Beispiel: Wenn ein Frontend von einer anderen Domain (z. B. http://127.0.0.3:5500) auf die API zugreifen möchte,
 * wird dies durch diese CORS-Konfiguration erlaubt.
 * 
 * @author Mykhaylo Zhvokeyvch
 * @version 1.0
 */
@Configuration
public class CorsConfig {

    /**
     * Erzeugt und konfiguriert ein {@link WebMvcConfigurer}-Bean, das für die CORS-Policy verantwortlich ist.
     * 
     * Diese Methode legt fest, dass alle Endpunkte unter "/api/**" nur von der angegebenen Origin-Adresse
     * "http://127.0.0.3:5500" aus zugänglich sind. Ausserdem werden nur die HTTP-Methoden "GET" und "POST"
     * erlaubt, und beliebige Header werden akzeptiert.
     * 
     * @return Ein konfiguriertes {@link WebMvcConfigurer}-Bean, das die CORS-Einstellungen festlegt.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://127.0.0.3:5500", "http://localhost")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("*");

            }
        };
    }
}