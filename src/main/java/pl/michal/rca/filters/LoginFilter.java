package pl.michal.rca.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/user/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String role = (String) httpRequest.getSession().getAttribute("role");
        if("ADMIN".equals(role)||"USER".equals(role)){
            chain.doFilter(request, response);}
        else{
            httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
