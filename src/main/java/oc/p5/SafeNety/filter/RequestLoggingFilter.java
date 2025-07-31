package oc.p5.SafeNety.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger=LogManager.getLogger(RequestLoggingFilter.class);

    @Override
            public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)
    throws IOException,ServletException{

        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;

        String method=req.getMethod();
        String uri=req.getRequestURI();
        String queryString=req.getQueryString();
        String fullUrl=(queryString!=null) ? uri+"?"+queryString:uri;
        String userAgent=req.getHeader("User-Agent");
        String ip=req.getRemoteAddr();

        logger.debug("➡️Requête reçue | IP:{},Méthode:{},URI:{},User-Agent:{}",ip,method,fullUrl,userAgent);

//Utilisationd'unwrapperpourcapturerlecodeHTTPfinal
        StatusCaptureResponseWrapper responseWrapper=new StatusCaptureResponseWrapper(res);

        try{
            chain.doFilter(request,responseWrapper);
        }finally{
            int status=responseWrapper.getStatus();
            if(status>=200&&status<300){
                logger.info("✅Requête réussie [{}{}]-Code HTTP:{}",method,fullUrl,status);

//}elseif(status>=400&&status<500){
//logger.warn("⚠️Erreurclient[{}{}]-CodeHTTP:{}",method,fullUrl,status);
//}elseif(status>=500){
//logger.error("❌Erreurserveur[{}{}]-CodeHTTP:{}",method,fullUrl,status);

            }else if(status>=400){
                logger.error("❌Erreur[{}{}]-CodeHTTP:{}",method,fullUrl,status);


            }else{
                logger.info("ℹ️Requêtetraitée[{}{}]-CodeHTTP:{}",method,fullUrl,status);
            }
        }
    }

//Wrapperpourcapturerlecodedestatutdelaréponse
    private static class StatusCaptureResponseWrapper extends HttpServletResponseWrapper{
        private int httpStatus=SC_OK;//200pardéfaut

        public StatusCaptureResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
                public void setStatus(int sc){
            super.setStatus(sc);
            this.httpStatus=sc;
        }

        @Override
                public void sendError(int sc,String msg)throws IOException{
            super.sendError(sc,msg);
            this.httpStatus=sc;
        }

        @Override
                public void sendError(int sc)throws IOException{
            super.sendError(sc);
            this.httpStatus=sc;
        }

        @Override
                public void sendRedirect(String location)throws IOException{
            super.sendRedirect(location);
            this.httpStatus=SC_MOVED_TEMPORARILY;
        }

        public int getStatus(){
            return this.httpStatus;
        }
    }
}
