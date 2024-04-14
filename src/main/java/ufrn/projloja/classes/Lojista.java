package ufrn.projloja.classes;

public class Lojista {
    private int id;
    private String nome;
    private String login;
    private String senha;

    public Lojista(int id) {
        this.id = id;
    }

    public Lojista(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
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
