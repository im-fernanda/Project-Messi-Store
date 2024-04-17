package ufrn.projloja.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ufrn.projloja.StaticDocs;
import ufrn.projloja.classes.Produto;
import ufrn.projloja.persistencia.ProdutoDAO;

@Controller
public class VerCarrinhoController {

    @GetMapping(value = "/verCarrinho")
    public void verCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String valorCookie = "";
        Boolean vazio = true;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(StaticDocs.clienteLogin)) {
                    valorCookie = cookie.getValue();
                    vazio = false;
                    break;
                }
            }
        }
        if (vazio) {
            response.sendRedirect("/listarProdutosCliente?msg=Carrinho vazio");
            return;
        }

        writer.println("<html><head><title>Carrinho de Compras</title><style>" +
                "table { border-collapse: collapse; width: 60%; margin: 0 auto; }" +
                "th, td { border: 1px solid black; padding: 8px; text-align: left; }" +
                "th { background-color: #f2f2f2; }" +
                "button { margin-top: 20px; margin-left: 10px; }" +
                "</style></head><body>" +
                "<h2 style=\"text-align: center;\">Carrinho de compras</h2>" +
                "<table>" +
                "<tr>" +
                "<th>Nome</th>" +
                "<th>Preço</th>" +
                "<th>Quantidade</th>" +
                "<th>Remover</th>" +
                "</tr>");

        Map<Integer, Integer> contagemIds = new HashMap<>();
        String[] ids = valorCookie.split("_");
        for (String id : ids) {
            int intId = Integer.parseInt(id);
            // Id do produto é o key, e o getValue as quantidades
            contagemIds.put(intId, contagemIds.getOrDefault(intId, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : contagemIds.entrySet()) {
            int id = entry.getKey();
            int quantidadeRepetida = entry.getValue();

            Produto p;
            ProdutoDAO pDAO = new ProdutoDAO();
            p = pDAO.getProdutoPorId(id);
            int estoque = pDAO.getQuantidade(id);
            writer.println("<tr><td>" + p.getNome() + "</td><td>" + p.getPreco() + "</td><td>");

            if (quantidadeRepetida == estoque) {
                writer.println( + quantidadeRepetida +
                        "<td><a href='/carrinhoServletFromVerCarrinho?id=" + p.getId()
                        + "&comando=remove'>Remover</a></td></tr>");
            } else {
                writer.println(+ quantidadeRepetida + "</td>" +
                        "<td><a href='/carrinhoServletFromVerCarrinho?id=" + p.getId() + "&comando=remove'>Remover</a></td></tr>");
            }

            StaticDocs.idsCarrinho.add(p.getId());
            StaticDocs.quantidadesCarrinho.add(quantidadeRepetida);
        }

        writer.println("<td colspan=\"4\" style=\"text-align: center;\">");
        writer.println("<button onclick=\"window.location.href='/listarProdutosCliente'\">Ver produtos</button>");
        writer.println("<button onclick=\"window.location.href='/finalizarCompra'\">Finalizar compra</button>");
        writer.println("<button onclick=\"window.location.href='home_cliente.html'\">Voltar para Home</button></td>");
        writer.println("</tr>");
        writer.println("</body></html>");
    }
}
