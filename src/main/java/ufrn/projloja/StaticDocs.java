package ufrn.projloja;

import java.util.ArrayList;

public class StaticDocs {
    // Atributos estáticos para auxiliar na implementação do carrinho
    public static String clienteLogin; // vai ser o nome do cookie
    public static ArrayList<Integer> idsCarrinho = new ArrayList<>(); // ids dos produtos que estão no carrinho
    public static ArrayList<Integer> quantidadesCarrinho = new ArrayList<>(); // quantidade de dos proddutos no carrinho
}
