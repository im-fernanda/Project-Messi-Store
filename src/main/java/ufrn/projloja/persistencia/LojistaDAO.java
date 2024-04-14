package ufrn.projloja.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ufrn.projloja.classes.Lojista;

public class LojistaDAO {
    private Conexao con;

    private String PRO = "SELECT * FROM Lojistas WHERE login=? AND senha=?";

    public LojistaDAO() {
        con = new Conexao("jdbc:postgresql://localhost:5432/db_loja", "postgres", "2005");
    }

    public Lojista procurar(String login, String senha) {
        ResultSet rs = null;
        Lojista l = null;

        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(PRO);
            ps.setString(1, login);
            ps.setString(2, senha);
            rs = ps.executeQuery();

            if(rs.next()){
                l = new Lojista(rs.getString("login"), rs.getString("senha"), rs.getString("nome"));
            }
            con.desconectar();
        }catch(Exception e){
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return l;
    }
}
