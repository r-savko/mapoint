package net.mapoint.filter;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

public class LoggingFilter implements Filter {

    private static Logger log = LogManager.getLogger(LoggingFilter.class);

    private static String getRequestData(final HttpServletRequest request) throws UnsupportedEncodingException {
        String payload = null;
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
            }
        }
        return payload;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(requestCacheWrapperObject, servletResponse);
            log.info("[Request][" + getRequestData(requestCacheWrapperObject) + "]" + "[" + requestCacheWrapperObject.getMethod() + "]"
                + requestCacheWrapperObject.getRequestURI());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
