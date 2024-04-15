package ufrn.projloja.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufrn.projloja.classes.Produto;
import ufrn.projloja.persistencia.ProdutoDAO;
import jakarta.servlet.http.Cookie;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CarrinhoController {

    @GetMapping(value = "/carrinhoServlet")
    protected void doTratarComando(@RequestParam("id") String produtoID, @RequestParam("comando") String command,
                                HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException {

        System.out.println("id prod:" + produtoID);

        // Verificar se o carrinho já existe


        // Se o carrinho não existir, criar um novo

        if (command.equals("add")){
            System.out.println("Estou add no carrinho");
            addAoCarrinho(produtoID, request, response);

        } else if (command.equals("remove")){
            //remover do carrinho
            //adicionar ao estoque
        }

    }
    @RequestMapping(value = "/addAoCarrinho", method = RequestMethod.GET)
    public void addAoCarrinho(@PathVariable("id") String produtoID,
                              HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Recuperar o produto do banco de dados pelo ID
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Nao sei se a função tá funcionando
        Produto produto = produtoDAO.getProdutoPorId(Integer.parseInt(produtoID));

        // Recuperar a lista de produtos do carrinho do cookie


        // Verificar se o produto já está no carrinho


        // Se o produto não estava no carrinho, adicioná-lo

        //Se o produto já estava no carrinho, incremetar a quantidade
        // Atualizar o cookie com a lista atualizada de produtos no carrinho


        // Redireciona de volta para a página de produtos
        System.out.println("Adiciondo com sucesso!");
        //response.sendRedirect("/listarProdutosCliente");
    }


}
