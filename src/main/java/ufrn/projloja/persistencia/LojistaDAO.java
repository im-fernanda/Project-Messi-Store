package ufrn.projloja.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ufrn.projloja.classes.Lojista;

public class LojistaDAO {
    private Conexao con;

    private String PRO = "SELECT id FROM Lojistas WHERE login=? AND senha=?";

    public LojistaDAO() {
        con = new Conexao("jdbc:postgresql://localhost:5432/db_loja", "postgres", "2005");
    }

    public boolean procurar(Lojista l) {
        boolean achou = false;
        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(PRO);
            ps.setString(1, l.getLogin());
            ps.setString(2, l.getSenha());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                achou = true;
            }
            con.desconectar();
        }catch(Exception e){
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return achou;
    }
}
