package ufrn.projloja.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.projloja.classes.Cliente;
import ufrn.projloja.persistencia.ClienteDAO;

import java.io.IOException;

@Controller
public class CadastroController {
    @RequestMapping(value = "/doCadastro", method = RequestMethod.POST)
    public void doCadastro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var nome = request.getParameter("nome");
        var login = request.getParameter("login");
        var senha = request.getParameter("senha");

        ClienteDAO cDAO = new ClienteDAO();
        Cliente c = new Cliente(nome, login, senha);
        try{
            cDAO.cadastrar(c);
            response.sendRedirect("index.html?msg=Cadastrado com sucesso!");

        } catch(Exception e){
            System.out.println(e.getMessage());
            response.sendRedirect("cadastro.html?msg=Erro no cadastro");
        }

    }
}
