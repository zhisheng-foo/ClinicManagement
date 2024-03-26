package filter;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managedbean.authenticationManagedBean;

public class StaffFilter implements Filter {

    @Inject
    private authenticationManagedBean authenticationManagedBean;

    public StaffFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        chain.doFilter(request1, response);
        
        if (authenticationManagedBean == null || authenticationManagedBean.getUserId() == -1) {
            //redirect to login page if user is not logged in and trying to access "staff/*" paths
            ((HttpServletResponse) response).sendRedirect(request1.getContextPath() + "/login.xhtml");
        } else if (authenticationManagedBean.isPatient()) {
            //redirect to notFound page if user is logged in as Patient and trying to access "staff/*" paths
            ((HttpServletResponse) response).sendRedirect(request1.getContextPath() + "/notFound.xhtml");
        } else { 
            //authenticated - continue
            chain.doFilter(request1, response);
        } 
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    @Override
    public void destroy() {
        //do nothing
    }
    
}
