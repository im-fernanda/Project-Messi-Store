package ufrn.projloja.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.projloja.classes.Cliente;
import ufrn.projloja.classes.Lojista;
import ufrn.projloja.persistencia.ClienteDAO;
import ufrn.projloja.persistencia.LojistaDAO;

import java.io.IOException;

@Controller
public class LoginController {

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = request.getParameter("login");
        var senha = request.getParameter("senha");

        ClienteDAO cDAO = new ClienteDAO();
        LojistaDAO lDAO = new LojistaDAO();

        Cliente cliente = cDAO.procurar(login, senha);
        Lojista lojista = lDAO.procurar(login, senha);

        if(cliente != null){
            HttpSession session = request.getSession(true);
            session.setAttribute("clienteLogado", true);

            response.sendRedirect("home_cliente.html");
        } else if (lojista != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("lojistaLogado", true);

            response.sendRedirect("home_lojista.html");
        }
        else {
            response.sendRedirect("index.html?msg=Login falhou");
        }
    }

    @RequestMapping(value="/logout")
    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("index.html?msg=Usuario saiu");
    }

}
