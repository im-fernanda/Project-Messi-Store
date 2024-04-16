package ufrn.projloja.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ufrn.projloja.StaticDocs;
import ufrn.projloja.classes.Produto;
import ufrn.projloja.persistencia.ProdutoDAO;

@Controller
public class VerCarrinhoController {
    
    @RequestMapping(value = "/verCarrinho", method = RequestMethod.GET)
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
            writer.println("<html><head><title>Carrinho de Compras</title><style>" +
                    "body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }" +
                    "h1 { text-align: center; }" +
                    "button { margin-top: 20px; margin-left: 10px; }" +
                    "</style></head><body>" +
                    "<h1>Carrinho Vazio</h1><br>" +
                    "<button onclick=\"window.location.href='home_cliente.html'\">Voltar para Home</button>" +
                    "</body></html>");
        }

        writer.println("<html><head><title>Lista de Produtos</title><style>" +
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
                "<th>Add</th>" +
                "<th>Remover</th>" +
                "</tr>");

        Map<Integer, Integer> contagemIds = new HashMap<>();
        String[] ids = valorCookie.split("_");
        for (String id : ids) {

            int intId = Integer.parseInt(id);
            contagemIds.put(intId, contagemIds.getOrDefault(intId, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : contagemIds.entrySet()) {
            int id = entry.getKey();
            int quantidadeRepetida = entry.getValue();
            Produto p;
            ProdutoDAO pDao = new ProdutoDAO();
            p = pDao.getProdutoPorId(id);
            int estoque = pDao.getQuantidade(id);
            if (quantidadeRepetida == estoque) {
                writer.println("<tr><td>" + p.getNome() + "</td><td>" + p.getPreco() + "</td><td>" + quantidadeRepetida + "</td><td>Máximo de estoque</td><td><a href='/carrinhoServletFromVerCarrinho?id=" + p.getId() + "&comando=remove'>Remover</a></td></tr>");
            } else {
                writer.println("<tr><td>" + p.getNome() + "</td><td>" + p.getPreco() + "</td><td>" + quantidadeRepetida + "</td><td><a href='/carrinhoServletFromVerCarrinho?id=" + p.getId() + "&comando=add'>Adicionar</a></td><td><a href='/carrinhoServletFromVerCarrinho?id=" + p.getId() + "&comando=remove'>Remover</a></td></tr>");
            }

            StaticDocs.idsCarrinho.add(p.getId());
            StaticDocs.quantidadesCarrinho.add(quantidadeRepetida);
        }
        writer.println("<tr>");
        writer.println("<td colspan=\"5\" style=\"text-align: center;\">");
        writer.println("<button onclick=\"window.location.href='/listaProdutosClientes'\">Ver produtos</button>");
        writer.println("<button onclick=\"window.location.href='/finalizarCompra'\">Finalizar compra</button>");
        writer.println("<button onclick=\"window.location.href='home_cliente.html'\">Voltar para Home</button></td>");
        writer.println("</tr>");

        writer.println("</body></html>");

    }
}
