package ufrn.projloja.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufrn.projloja.classes.Produto;
import ufrn.projloja.persistencia.ProdutoDAO;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Controller
public class ListaProdutosController {

    @RequestMapping(value = "/listarProdutos", method = RequestMethod.GET)
    public void getAllProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtos = produtoDAO.listarProdutos();

        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<html> <head> <title> Lista de Produtos </title> <style>" +
                "table { border-collapse: collapse; width: 80%; margin: 0 auto; }" + // Centraliza a tabela e define a largura como 80%
                "th, td { border: 1px solid black; padding: 8px; text-align: left; }" +
                "th { background-color: #f2f2f2; }" + "button { margin-top: 20px; }" +
                "</style></head> <body> <h2 style=\"text-align: center;\">Lista de Produtos</h2> <table>");

        writer.println("<tr>");
        writer.println("<th>Nome</th>");
        writer.println("<th>Descrição</th>");
        writer.println("<th>Preço</th>");
        writer.println("<th>Estoque</th>");
        writer.println("<th>Carrinho</th>");
        writer.println("</tr>");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (Produto produto : produtos) {
            writer.println("<tr>");
            writer.println("<td>" + produto.getNome() + "</td>");
            writer.println("<td>" + produto.getDescricao() + "</td>");
            writer.println("<td>R$ " + decimalFormat.format(produto.getPreco()) + "</td>");
            writer.println("<td>" + produto.getQuantidade() + "</td>");
            writer.println("<td><a href=\"/adicionarAoCarrinho?id=" + produto.getId() + "\">Adicionar ao Carrinho</a></td>");
            writer.println("</tr>");
        }
        writer.println("</table>");


        writer.println("</body> </html>");

    }


}

