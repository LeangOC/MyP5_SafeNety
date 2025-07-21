package oc.p5.SafeNety.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String method = req.getMethod();
        String uri = req.getRequestURI();
        String queryString = req.getQueryString(); // contient stationNumber=1
        String fullUrl = (queryString != null) ? uri + "?" + queryString : uri;
        String userAgent = req.getHeader("User-Agent");
        logger.info("URI: {}", fullUrl);
        //logger.info("Récupération des personnes couvertes par la station {}", 2);
        //logger.debug("HTTP {} request to '{}' from IP: {}", method, fullUrl, req.getRemoteAddr());
        logger.debug("Request from IP: {}, method: {}, URI: {}, User-Agent: {}", req.getRemoteAddr(), req.getMethod(), fullUrl, userAgent);
        chain.doFilter(request, response);
    }
}
