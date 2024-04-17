package ufrn.projloja.persistencia;

import java.sql.*;

import ufrn.projloja.classes.Cliente;

public class ClienteDAO {
    private Conexao con;
    private String CAD = "INSERT INTO Clientes (nome, login, senha) VALUES (?, ?, ?)";
    private String PROCURAR = "SELECT * FROM Clientes WHERE login=? AND senha=?";
    private String GETBYLOGIN = "SELECT * FROM Clientes WHERE login=?";

    public ClienteDAO() {
        con = new Conexao("jdbc:postgresql://localhost:5432/db_loja", "postgres", "2005");
    }

    public void cadastrar(Cliente c) {
        try {
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(CAD);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getLogin());
            ps.setString(3, c.getSenha());
            ps.execute();
            con.desconectar();
        } catch (Exception e) {
            System.out.println("Erro na inclusão: " + e.getMessage());
        }
    }

    public Cliente procurar(String login, String senha) {
        ResultSet rs = null;
        Cliente c = null;

        try {
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(PROCURAR);
            ps.setString(1, login);
            ps.setString(2, senha);

            rs = ps.executeQuery();
            if(rs.next()){
                c = new Cliente(rs.getString("nome"), rs.getString("login"), rs.getString("senha"));
            }
            con.desconectar();
        } catch(Exception e){
            System.out.println("Erro na busca: " + e.getMessage());
        }

        return c;
    }

    public Integer selecionarId(Cliente c) {
        Integer id = null;
        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(PROCURAR);
            ps.setString(1, c.getLogin());
            ps.setString(2, c.getSenha());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("id");
            }
            con.desconectar();
        } catch(Exception e){
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return id;
    }

    public Boolean getByLogin(String login) {
        ResultSet rs = null;
        Boolean achou = false;

        try {
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(GETBYLOGIN);
            ps.setString(1, login);

            rs = ps.executeQuery();
            if(rs.next()){
                achou = true;
            }
            con.desconectar();
        } catch(Exception e){
            System.out.println("Erro na busca: " + e.getMessage());
        }

        return achou;
    }
}
