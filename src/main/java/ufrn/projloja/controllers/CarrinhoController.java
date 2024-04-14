package ufrn.projloja.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.projloja.classes.Produto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarrinhoController {

    @RequestMapping(value = "/addAoCarrinho", method = RequestMethod.GET)
    public void adicionarAoCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Produto> carrinho = new ArrayList<>();
        // Simulando a adição de um produto ao carrinho
        String produtoId = request.getParameter("id");
        Produto produto = buscarProdutoPorId(Integer.parseInt(produtoId));
        if (produto != null) {
            carrinho.add(produto);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/listarProdutos");
            dispatcher.forward(request, response);
        }

    }

    // Método de exemplo para buscar um produto pelo ID
    private Produto buscarProdutoPorId(int id) {
        //Logica para buscar o produto
        return new Produto(id, "Produto " + id, 10.0F, 1, "Descrição do Produto " + id);
    }
}
