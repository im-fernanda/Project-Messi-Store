package ufrn.projloja.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.projloja.classes.Carrinho;
import ufrn.projloja.classes.Produto;
import ufrn.projloja.controllers.CarrinhoController;
import ufrn.projloja.persistencia.ProdutoDAO;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VerCarrinhoController {
    @RequestMapping(value = "/verCarrinho", method = RequestMethod.GET)
    public void getCarrinho(HttpServletRequest request, HttpServletResponse response,
                            @CookieValue(value = "carrinho", defaultValue = "") String carrinhoCookie)
            throws IOException {

        // Recupera a lista de produtos do carrinho a partir do cookie
        List<Produto> produtosNoCarrinho = CarrinhoController.getProdutosFromCookie(request, carrinhoCookie);


        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<html> <head> <title> Carrinho de Compras </title> <style>" +
                "table { border-collapse: collapse; width: 80%; margin: 0 auto; }" + // Centraliza a tabela e define a largura como 80%
                "th, td { border: 1px solid black; padding: 8px; text-align: left; }" +
                "th { background-color: #f2f2f2; }" + "button { margin-top: 20px; }" +
                "</style></head> <body> <h2 style=\"text-align: center;\">Carrinho de Compras</h2> <table>");

        writer.println("<tr>");
        writer.println("<th>Nome</th>");
        writer.println("<th>Preço</th>");
        writer.println("<th>Quantidade</th>");
        writer.println("<th>Ação</th>");
        writer.println("</tr>");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (Produto item : produtosNoCarrinho) {
            writer.println("<tr>");
            writer.println("<td>" + item.getNome() + "</td>");
            writer.println("<td>R$ " + decimalFormat.format(item.getPreco()) + "</td>");
            writer.println("<td>" + item.getQuantidade() + "</td>");
            writer.println("<td>");
            // Link para remover item do carrinho
            writer.println("<a href='/removerDoCarrinho?id=" + item.getId() + "'>Remover</a>");
            writer.println("<a href='/addAoCarrinho?id=" + item.getId() + "'>Adicionar</a>");
            writer.println("</td>");
            writer.println("</tr>");
        }

        writer.println("</table></body></html>");
    }
}
