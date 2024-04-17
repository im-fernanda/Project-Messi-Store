package ufrn.projloja;

import java.util.ArrayList;

public class StaticDocs {
    // Atributos estáticos para auxiliar na implementação do carrinho
    public static String clienteLogin; //Será o nome do cookie
    public static ArrayList<Integer> idsCarrinho = new ArrayList<>(); //Ids dos produtos que estão no carrinho
    public static ArrayList<Integer> quantidadesCarrinho = new ArrayList<>(); //Quantidade dos proddutos no carrinho
}
