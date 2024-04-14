package ufrn.projloja.persistencia;

import java.sql.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ufrn.projloja.classes.Produto;
import ufrn.projloja.persistencia.Conexao;

public class ProdutoDAO {
    private Conexao con;
    private String ADD = "INSERT INTO Produtos(nome, preco, quantidade, descricao) VALUES (?, ?, ?, ?)";
    private String DEL = "DELETE FROM Produtos WHERE id = ?";
    private String UPD = "UPDATE Produtos SET quantidade = ? WHERE id = ?";
    private String REL = "SELECT * FROM Produtos";

    public ProdutoDAO() {
        con = new Conexao("jdbc:postgresql://localhost:5432/db_loja", "postgres", "2005");
    }

    public void addProduto(Produto p){
        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(ADD);
            ps.setString(1, p.getNome());
            ps.setFloat(2, p.getPreco());
            ps.setInt(3, p.getQuantidade());
            ps.setString(4, p.getDescricao());
            ps.execute();
            con.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }

    public void deleteProduto(int id){
        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(DEL);
            ps.setInt(1, id);
            ps.execute();
            con.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }

    public void updateQuantidade(int id, int quantidade){
        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(UPD);
            ps.setInt(1, quantidade);
            ps.setInt(2, id);
            ps.execute();
            con.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }

    public List<Produto> listarProdutos(){
        List<Produto> lista = new ArrayList<>();
        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(REL);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Produto p = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getFloat("preco"), rs.getInt("quantidade"), rs.getString("descricao"));
                lista.add(p);
            }
            con.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão: " + e.getMessage());
        }

        return lista;
    }
}
