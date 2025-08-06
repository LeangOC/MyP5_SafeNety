package oc.p5.SafeNety.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Crée un wrapper personnalisé pour permettre de lire plusieurs fois la requête
        BufferedRequestWrapper wrappedRequest = new BufferedRequestWrapper(req);

        String method = req.getMethod();
        String uri = req.getRequestURI();
        String queryString = req.getQueryString();
        String fullUrl = (queryString != null) ? uri + "?" + queryString : uri;
        String userAgent = req.getHeader("User-Agent");
        String ip = req.getRemoteAddr();

        // Lire le corps de la requête
        String body = wrappedRequest.getRequestBody();  // Utilise le wrapper ici

        // Si le corps de la requête existe, loggue-le
        if (body != null && !body.isEmpty()) {
            logger.debug("➡️ Requête reçue | IP: {}, Méthode: {}, URI: {}, User-Agent: {}, Corps: {}",
                    ip, method, fullUrl, userAgent, body);
        } else {
            logger.debug("➡️ Requête reçue | IP: {}, Méthode: {}, URI: {}, User-Agent: {}", ip, method, fullUrl, userAgent);
        }

        // Utilisation d'un wrapper pour capturer le code HTTP final
        StatusCaptureResponseWrapper responseWrapper = new StatusCaptureResponseWrapper(res);

        try {
            chain.doFilter(wrappedRequest, responseWrapper);  // Utilise aussi le wrapper pour que Spring puisse lire la requête
        } finally {
            int status = responseWrapper.getStatus();
            if (status >= 200 && status < 300) {
                logger.info("✅ Requête réussie [{}{}] - Code HTTP: {}", method, fullUrl, status);
            } else if (status >= 400) {
                logger.error("❌ Erreur [{}{}] - Code HTTP: {}", method, fullUrl, status);
            } else {
                logger.info("ℹ️ Requête traitée [{}{}] - Code HTTP: {}", method, fullUrl, status);
            }
        }
    }

    // Wrapper pour permettre de lire le corps de la requête plusieurs fois
    public static class BufferedRequestWrapper extends HttpServletRequestWrapper {
        private byte[] cachedBody;

        public BufferedRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            // Lire le corps de la requête et le stocker en mémoire
            cachedBody = getRequestBody(request).getBytes();
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new CachedServletInputStream(cachedBody);  // Utilisation de CachedServletInputStream
        }

        // Nouvelle méthode pour obtenir le corps de la requête (décalé ici dans le wrapper)
        public String getRequestBody() {
            return new String(cachedBody);
        }

        // Méthode pour lire le corps de la requête
        private String getRequestBody(HttpServletRequest request) {
            StringBuilder body = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            } catch (IOException e) {
                logger.error("Erreur lors de la lecture du corps de la requête", e);
            }
            return body.toString();
        }
    }

    // Classe qui étend ServletInputStream pour permettre la lecture multiple
    public static class CachedServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream byteArrayInputStream;

        public CachedServletInputStream(byte[] data) {
            this.byteArrayInputStream = new ByteArrayInputStream(data);
        }

        @Override
        public int read() throws IOException {
            return byteArrayInputStream.read();
        }

        // Implémentation de setReadListener (même si ce n'est pas utilisé ici)
        @Override
        public void setReadListener(ReadListener readListener) {
            // Pas de gestion asynchrone ici
        }

        @Override
        public boolean isReady() {
            return true; // Toujours prêt à être lu
        }

        @Override
        public boolean isFinished() {
            return byteArrayInputStream.available() == 0;  // Signale que le flux est terminé lorsque tout est lu
        }
    }

    // Wrapper pour capturer le code de statut de la réponse
    private static class StatusCaptureResponseWrapper extends HttpServletResponseWrapper {
        private int httpStatus = SC_OK; // 200 par défaut

        public StatusCaptureResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void setStatus(int sc) {
            super.setStatus(sc);
            this.httpStatus = sc;
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            super.sendError(sc, msg);
            this.httpStatus = sc;
        }

        @Override
        public void sendError(int sc) throws IOException {
            super.sendError(sc);
            this.httpStatus = sc;
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            super.sendRedirect(location);
            this.httpStatus = SC_MOVED_TEMPORARILY;
        }

        public int getStatus() {
            return this.httpStatus;
        }
    }
}
