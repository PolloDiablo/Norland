package ca.casualt.norland.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import ca.casualt.norland.persistence.Security;
import ca.casualt.norland.shared.RequestAccess;

/**
 * Checks authorization.
 * 
 * @author Tony
 */
public final class Auth implements javax.servlet.Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest arg0, final ServletResponse arg1,
            final FilterChain arg2) throws IOException, ServletException {
        final String id = RequestAccess.getID(arg0);
        final String token = RequestAccess.getToken(arg0);
        final boolean auth = Security.getTheInstance().isAuthorizedToWriteStats(id, token);
        if (auth) {
            arg2.doFilter(arg0, arg1);
        } else {
            doAuthFailureResponse(arg0, arg1);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    /*
     * Support.
     */
    private void doAuthFailureResponse(ServletRequest arg0, ServletResponse arg1)
            throws IOException {
        HttpServletResponse resp = ((HttpServletResponse) arg1);
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final PrintWriter writer = arg1.getWriter();
        writer.println("Unauthorized. :(");
    }
}
