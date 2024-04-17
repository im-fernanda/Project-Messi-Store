package ufrn.projloja;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//Filtra as páginas que o lojista não pode acessar
@WebFilter(urlPatterns = {"/home_cliente.html", "/verCarrinho"})
public class FiltroLojista implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        HttpSession session = request.getSession(false);

        if (session == null){
            response.sendRedirect("index.html?msg=Voce precisa logar antes");
        } else {
            Boolean lojistaLogado = (Boolean) session.getAttribute("lojistaLogado");
            if (lojistaLogado != null && lojistaLogado.booleanValue()) {
                response.sendRedirect("home_lojista.html?msg=Voce precisa logar como cliente antes");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
