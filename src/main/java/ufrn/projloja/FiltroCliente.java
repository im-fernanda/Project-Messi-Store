package ufrn.projloja;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


//Filtra as páginas que o cliente não pode acessar
@WebFilter(urlPatterns = {"/home_lojista.html", "/cadastro_produto.html"})
public class FiltroCliente implements Filter {

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
            Boolean clienteLogado = (Boolean) session.getAttribute("clienteLogado");
            if (clienteLogado != null && clienteLogado.booleanValue()) {
                response.sendRedirect("home_cliente.html?msg=Voce n possui autorizacao");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
