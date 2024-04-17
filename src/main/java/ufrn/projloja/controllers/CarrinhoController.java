package ufrn.projloja.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ufrn.projloja.StaticDocs;
import ufrn.projloja.persistencia.ProdutoDAO;
@Controller
public class CarrinhoController {
    String idProdutos = "";

    @GetMapping("/carrinhoServlet")
    public void controlCarrinho(@RequestParam("id") String idProduto, @RequestParam("comando") String comando, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        String nomeCarrinho = StaticDocs.clienteLogin;

        if (comando.equals("add")) {
            ProdutoDAO pDAO = new ProdutoDAO();
            int quantidade = pDAO.getQuantidade(Integer.parseInt(idProduto));

            if (quantidade == 0) {
                response.sendRedirect("/listarProdutosCliente?msg=Produto sem estoque");
                return;
            }
            // Percorre os cookies existentes
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(nomeCarrinho)) {
                        // Caso exista um cookie do cliente atual, pega o que está guardado nele
                        idProdutos = cookie.getValue();
                        break;
                    }
                }
            }
            // Adiciona o produto adicionado ao cookie já existente
            idProdutos += idProduto + "_";

            // Destruir cookie antes de atualiza-lo
            Cookie cookieToDelete = new Cookie(nomeCarrinho, null); // Crie um novo cookie com o mesmo nome do cookie a ser destruído
            cookieToDelete.setMaxAge(0); // Defina o tempo de vida do cookie como zero para que ele expire imediatamente
            response.addCookie(cookieToDelete); // Adicione o cookie à resposta HTTP para que ele seja removido pelo navegador

            // Cria um novo cookie guardando os ids dos produtos

            Cookie carrinho = new Cookie(nomeCarrinho, idProdutos);
            carrinho.setMaxAge(60 * 60 * 48); // tempo de vida em segundos
            response.addCookie(carrinho);
            response.sendRedirect("/listarProdutosCliente?msg=Produto adicionado");

        } else if (comando.equals("remove")) {
            idProduto = idProduto + "_";
            idProdutos = idProdutos.replaceFirst(idProduto, ""); // retira o id do produto da string

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(nomeCarrinho)) {
                        // Caso exista um cookie do cliente atual, pega o que está guardado nele
                        idProdutos = cookie.getValue();
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        break;
                    }
                }
            }

            // Verifica se o carrinho ainda tem produtos
            if (idProdutos != "") { // Caso não tenha ficado vazio, instancia novamente o cookie com o resto dos ids
                Cookie carrinho = new Cookie(StaticDocs.clienteLogin, idProdutos);
                carrinho.setMaxAge(172800);
                response.addCookie(carrinho);
                response.sendRedirect("/verCarrinho?msg=Produto removido");
            } else if (idProdutos.equals("")) { // Caso tenha zerado o carrinho, apaga o cookie
                Cookie carrinho = new Cookie(StaticDocs.clienteLogin, idProdutos);
                carrinho.setMaxAge(0);
                response.addCookie(carrinho);
                response.sendRedirect("/listarProdutosCliente?msg=Carrinho vazio");
            }
        }
    }

    @GetMapping("/carrinhoServletFromVerCarrinho")
    public void controlCarrinhoFromVerCarrinho(@RequestParam("id") String idProduto, @RequestParam("comando") String comando,
                                               HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nomeCarrinho = StaticDocs.clienteLogin;

        if (comando.equals("add")) {
            ProdutoDAO pDAO = new ProdutoDAO();
            int quantidade = pDAO.getQuantidade(Integer.parseInt(idProduto));
            // Percorre os cookies existentes
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(nomeCarrinho)) {
                        // Caso exista um cookie do cliente atual, pega o que está guardado nele
                        idProdutos = cookie.getValue();
                        break;
                    }
                }
            }
            // Adiciona o produto adicionado ao cookie já existente
            idProdutos += idProduto + "_";

            // Cria um novo cookie guardando os ids dos produtos
            // carrinho.setValue(idProdutos);
            Cookie carrinho = new Cookie(nomeCarrinho, idProdutos);
            carrinho.setMaxAge(60 * 60 * 48);
            response.addCookie(carrinho);
            response.sendRedirect("/verCarrinho?msg=Produto adicionado");

        } else if (comando.equals("remove")) {
            System.out.println("Cookie antes: " + idProdutos);
            System.out.println("Produto: " + idProduto);

            idProduto = idProduto + "_";

            // Encontra a posição da primeira ocorrência do idProduto na string idProdutos
            StringBuilder idProdutosBuilder = new StringBuilder(idProdutos);
            int index = idProdutosBuilder.indexOf(idProduto);

            // Se encontrar o idProduto na string idProdutos, remove-o
            if (index != -1) {
                // Remove o idProduto da string idProdutos
                idProdutosBuilder.delete(index, index + idProduto.length());
                // Atualiza a variável idProdutos com a nova string sem o idProduto
                idProdutos = idProdutosBuilder.toString();
            }

            System.out.println("Cookie depois: " + idProdutos);

            // Verifica se o carrinho ainda tem produtos
            if (idProdutos != "") { // Caso não tenha ficado vazio, instancia novamente o cookie com o resto dos ids
                if (nomeCarrinho != null && !nomeCarrinho.isEmpty()) {
                    Cookie carrinho = new Cookie(nomeCarrinho, idProdutos);
                    response.addCookie(carrinho);
                    response.sendRedirect("/verCarrinho?msg=Produto removido");
                } else {
                    // Trata o caso em que nomeCarrinho é nulo ou vazio
                    String nomePadrao = StaticDocs.clienteLogin;
                    Cookie carrinho = new Cookie(nomePadrao, idProdutos);
                    carrinho.setMaxAge(60 * 60 * 48);
                    response.addCookie(carrinho);
                    response.sendRedirect("/verCarrinho?msg=Produto removido");
                }

            } else if (idProdutos.equals("")) { // Caso tenha zerado o carrinho, apaga o cookie
                Cookie carrinho = new Cookie(nomeCarrinho, idProdutos);
                carrinho.setMaxAge(0);
                response.addCookie(carrinho);
                response.sendRedirect("/listarProdutosCliente?msg=Carrinho vazio");
            }
        }
    }

    @GetMapping("/finalizarCompra")
    public void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Integer> arrayIds = StaticDocs.idsCarrinho;
        ArrayList<Integer> arrayQuantidades = StaticDocs.quantidadesCarrinho;
        ProdutoDAO pDAO = new ProdutoDAO();
        int quantidade;
        int novaQuantidade;

        for (int i = 0; i < arrayIds.size(); i++) {
            quantidade = pDAO.getQuantidade(arrayIds.get(i));
            novaQuantidade = quantidade - arrayQuantidades.get(i);

            pDAO.updateQuantidade(arrayIds.get(i), novaQuantidade);
        }

        // Apagando o cookie do carrinho
        String nomeCarrinho = StaticDocs.clienteLogin;
        // Percorre os cookies existentes
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(nomeCarrinho)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                idProdutos = "";
                StaticDocs.idsCarrinho = new ArrayList<>();
                StaticDocs.quantidadesCarrinho = new ArrayList<>();
                response.sendRedirect("home_cliente.html?msg=Compra realizada");
            }
        }

    }
}