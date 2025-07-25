package oc.p5.SafeNety.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String method = req.getMethod();
        String uri = req.getRequestURI();
        String queryString = req.getQueryString();
        String fullUrl = (queryString != null) ? uri + "?" + queryString : uri;
        String userAgent = req.getHeader("User-Agent");

        logger.info("Received request: {} {}", method, fullUrl);
        logger.debug("Request details: IP={}, User-Agent={}", req.getRemoteAddr(), userAgent);

        try {
            chain.doFilter(request, response);
            int status = res.getStatus();
            logger.info("Response status: {} for {}", status, fullUrl);
        } catch (Exception e) {
            logger.error("Exception while processing request: {} {}, error: {}", method, fullUrl, e.getMessage(), e);
            throw e;
        }
    }
}
