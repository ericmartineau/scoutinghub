package scoutcert

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.Cookie
import java.security.Principal
import javax.servlet.http.HttpSession
import javax.servlet.ServletInputStream
import javax.servlet.RequestDispatcher
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * User: eric
 * Date: 3/16/11
 * Time: 9:52 PM
 */
public class GrailsHttpServletRequest implements HttpServletRequest{

    private HttpServletRequest httpServletRequest;

    GrailsHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest
    }

    @Override
    public String getAuthType() {
        return httpServletRequest.getAuthType();
    }

    @Override
    public String getContextPath() {
        return httpServletRequest.getContextPath();
    }

    @Override
    public Cookie[] getCookies() {
        return httpServletRequest.getCookies();
    }

    @Override
    public long getDateHeader(String s) {
        return httpServletRequest.getDateHeader(s);
    }

    @Override
    public String getHeader(String s) {
        return httpServletRequest.getHeader(s);
    }

    @Override
    public Enumeration getHeaderNames() {
        return httpServletRequest.getHeaderNames();
    }

    @Override
    public Enumeration getHeaders(String s) {
        return httpServletRequest.getHeaders(s);
    }

    @Override
    public int getIntHeader(String s) {
        return httpServletRequest.getIntHeader(s);
    }

    @Override
    public String getMethod() {
        return httpServletRequest.getMethod();
    }

    @Override
    public String getPathInfo() {
        return httpServletRequest.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        return httpServletRequest.getPathTranslated();
    }

    @Override
    public String getQueryString() {
        return httpServletRequest.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return httpServletRequest.getRemoteUser();
    }

    @Override
    public String getRequestedSessionId() {
        return httpServletRequest.getRequestedSessionId();
    }

    @Override
    public String getRequestURI() {
        return httpServletRequest.getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        String requestUri = this.getRequestURI();
        requestUri = requestUri.substring(getContextPath().length())
        return new StringBuffer(ConfigurationHolder.config.grails.serverURL + requestUri);
    }

    @Override
    public String getServletPath() {
        return httpServletRequest.getServletPath();
    }

    @Override
    public HttpSession getSession() {
        return httpServletRequest.getSession();
    }

    @Override
    public HttpSession getSession(boolean b) {
        return httpServletRequest.getSession(b);
    }

    @Override
    public Principal getUserPrincipal() {
        return httpServletRequest.getUserPrincipal();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return httpServletRequest.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return httpServletRequest.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return httpServletRequest.isRequestedSessionIdFromUrl();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return httpServletRequest.isRequestedSessionIdValid();
    }

    @Override
    public boolean isUserInRole(String s) {
        return httpServletRequest.isUserInRole(s);
    }

    @Override
    public Object getAttribute(String s) {
        return httpServletRequest.getAttribute(s);
    }

    @Override
    public Enumeration getAttributeNames() {
        return httpServletRequest.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return httpServletRequest.getCharacterEncoding();
    }

    @Override
    public int getContentLength() {
        return httpServletRequest.getContentLength();
    }

    @Override
    public String getContentType() {
        return httpServletRequest.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    @Override
    public String getLocalAddr() {
        return httpServletRequest.getLocalAddr();
    }

    @Override
    public Locale getLocale() {
        return httpServletRequest.getLocale();
    }

    @Override
    public Enumeration getLocales() {
        return httpServletRequest.getLocales();
    }

    @Override
    public String getLocalName() {
        return httpServletRequest.getLocalName();
    }

    @Override
    public int getLocalPort() {
        return httpServletRequest.getLocalPort();
    }

    @Override
    public String getParameter(String s) {
        return httpServletRequest.getParameter(s);
    }

    @Override
    public Map getParameterMap() {
        return httpServletRequest.getParameterMap();
    }

    @Override
    public Enumeration getParameterNames() {
        return httpServletRequest.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String s) {
        return httpServletRequest.getParameterValues(s);
    }

    @Override
    public String getProtocol() {
        return httpServletRequest.getProtocol();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return httpServletRequest.getReader();
    }

    @Override
    public String getRealPath(String s) {
        return httpServletRequest.getRealPath(s);
    }

    @Override
    public String getRemoteAddr() {
        return httpServletRequest.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return httpServletRequest.getRemoteHost();
    }

    @Override
    public int getRemotePort() {
        return httpServletRequest.getRemotePort();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return httpServletRequest.getRequestDispatcher(s);
    }

    @Override
    public String getScheme() {
        return httpServletRequest.getScheme();
    }

    @Override
    public String getServerName() {
        return httpServletRequest.getServerName();
    }

    @Override
    public int getServerPort() {
        return httpServletRequest.getServerPort();
    }

    @Override
    public boolean isSecure() {
        return httpServletRequest.isSecure();
    }

    @Override
    public void removeAttribute(String s) {
        httpServletRequest.removeAttribute(s);
    }

    @Override
    public void setAttribute(String s, Object o) {
        httpServletRequest.setAttribute(s, o);
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        httpServletRequest.setCharacterEncoding(s);
    }

}

