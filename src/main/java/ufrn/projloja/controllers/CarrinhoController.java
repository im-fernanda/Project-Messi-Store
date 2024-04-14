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

    @RequestMapping(value = "/carrinhoServlet", method = RequestMethod.POST)
    protected void doTratarPost(@RequestParam("id") String id, @RequestParam("comando") String command,
                                HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException {
        String idS = id.replaceAll("[^0-9]","");
        Integer produtoId = Integer.parseInt(idS);

        //String command = request.getParameter("comando");
        System.out.println(command);

        if (command.equals("add")){
            System.out.println("Estou add no carrinho");
            adicionarProdAoCarrinho(produtoId, request, response);

        } else if (command.equals("remove")){
            //remover do carrinho
            //adicionar ao estoque
        }

    }
    @RequestMapping(value = "/addAoCarrinho/{id}", method = RequestMethod.GET)
    public void addAoCarrinho(@PathVariable("id") String id,
                              HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idS = id.replaceAll("[^0-9]", "");
        Integer produtoId = Integer.valueOf(idS);

        // Adiciona o produto ao carrinho
        adicionarProdAoCarrinho(produtoId, request, response);

        // Redireciona de volta para a página de produtos
        response.sendRedirect("/listarProdutosCliente");
    }

    private void adicionarProdAoCarrinho(int produtoId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Recuperar o produto do banco de dados pelo ID
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto = produtoDAO.getProdutoPorId(produtoId);

        // Recuperar a lista de produtos do carrinho do cookie
        List<Produto> produtosNoCarrinho = getProdutosFromCookie(request, "carrinho");

        // Verificar se o produto já está no carrinho
        boolean produtoJaNoCarrinho = false;
        for (Produto item : produtosNoCarrinho) {
            if (item.getId() == produtoId) {
                // Se o produto já está no carrinho, incrementar a quantidade e marcar como encontrado
                item.setQuantidade(item.getQuantidade() + 1);
                produtoJaNoCarrinho = true;
                break;
            }
        }

        // Se o produto não estava no carrinho, adicioná-lo
        if (!produtoJaNoCarrinho) {
            produtosNoCarrinho.add(produto);
        }

        // Atualizar o cookie com a lista atualizada de produtos no carrinho
        String carrinhoAtualizado = serializeProdutos(produtosNoCarrinho);
        Cookie cookie = new Cookie("carrinho", URLEncoder.encode(carrinhoAtualizado, "UTF-8"));
        response.addCookie(cookie);
        }

        public static List<Produto> getProdutosFromCookie(HttpServletRequest request, String nomeDoCookie) {
        List<Produto> produtosNoCarrinho = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("carrinho")) {
                    String cookieValue = cookie.getValue();
                    try {
                        String decodedValue = URLDecoder.decode(cookieValue, "UTF-8");
                        produtosNoCarrinho = deserializeProdutos(decodedValue);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return produtosNoCarrinho;
    }
    private String serializeProdutos(List<Produto> produtos) {
        StringBuilder sb = new StringBuilder();
        for (Produto produto : produtos) {
            sb.append(produto.getId()).append(",");
        }
        // Remove a última vírgula, se houver
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    public static List<Produto> deserializeProdutos(String serializedProdutos) {
        List<Produto> produtos = new ArrayList<>();
        if (!serializedProdutos.isEmpty()) {
            String[] produtoTokens = serializedProdutos.split(",");
            for (String produtoToken : produtoTokens) {
                String[] atributos = produtoToken.split(":");
                if (atributos.length == 5) {
                    int id = Integer.parseInt(atributos[0]);
                    String nome = atributos[1];
                    float preco = Float.parseFloat(atributos[2]);
                    int quantidade = Integer.parseInt(atributos[3]);
                    String descricao = atributos[4];
                    Produto produto = new Produto(id, nome, preco, quantidade, descricao);
                    produtos.add(produto);
                }
            }
        }
        return produtos;
    }
}
