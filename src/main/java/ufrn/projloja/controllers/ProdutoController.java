package ufrn.projloja.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ufrn.projloja.classes.Produto;
import ufrn.projloja.persistencia.ProdutoDAO;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @RequestMapping(value = "/listarProdutos", method = RequestMethod.GET)
    public void getAllProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtos = produtoDAO.listarProdutos();

        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<html> <head> <title> Lista de Produtos </title> </head> <body> <table>");

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
        writer.println("</table></body> </html>");
    }

    @RequestMapping("/cadastrarProduto")
    public void cadastrarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException{

        var nome = request.getParameter("nome");
        var descricao = request.getParameter("descricao");
        var preco = Float.valueOf(request.getParameter("preco"));
        var quantidade = Integer.parseInt(request.getParameter("quantidade"));

        Produto p = new Produto();
        ProdutoDAO pd = new ProdutoDAO();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setPreco(preco);
        p.setQuantidade(quantidade);
        pd.addProduto(p);

        HttpSession session = request.getSession(false);
        if(session != null){
            response.sendRedirect("home_lojista.html");
        }else{
            response.sendRedirect("login.html");
        }

    }
}

