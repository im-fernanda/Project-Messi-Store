package ufrn.projloja.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.projloja.classes.Carrinho;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Controller
public class VerCarrinhoController {
    @RequestMapping(value = "/verCarrinho", method = RequestMethod.GET)
    public void verCarrinho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        writer.println("<th>Total</th>");
        writer.println("</tr>");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");


//        for (Carrinho item : carrinho.getProdutos()) {
//            writer.println("<tr>");
//            writer.println("<td>" + item.getProduto().getNome() + "</td>");
//            writer.println("<td>R$ " + decimalFormat.format(item.getProduto().getPreco()) + "</td>");
//            writer.println("<td>" + item.getQuantidade() + "</td>");
//            writer.println("<td>R$ " + decimalFormat.format(item.getSubtotal()) + "</td>");
//            writer.println("</tr>");
//        }


//        writer.println("<tr>");
//        writer.println("<td colspan=\"3\"><strong>Total</strong></td>");
//        writer.println("<td><strong>R$ " + decimalFormat.format(carrinho.getTotal()) + "</strong></td>");
//        writer.println("</tr>");
//
//        writer.println("</table>");
//
//        writer.println("</body> </html>");

    }
}
