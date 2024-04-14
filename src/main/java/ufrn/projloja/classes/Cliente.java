package ufrn.projloja.classes;

public class Cliente {
    private int id;
    private String nome;
    private String login;
    private String senha;

    public Cliente(int id) {
        this.id = id;
    }

    public Cliente(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Cliente(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
