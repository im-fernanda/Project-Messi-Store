package ufrn.projloja.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ufrn.projloja.classes.Produto;
import ufrn.projloja.persistencia.ProdutoDAO;

import java.io.IOException;

@Controller
public class ProdutoController {

    @RequestMapping("/cadastrarProduto")
    public void cadastrarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {

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



    }
}