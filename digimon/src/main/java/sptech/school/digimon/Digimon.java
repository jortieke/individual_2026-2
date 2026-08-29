package sptech.school.digimon;

public class Digimon {
    private Integer id;
    private String nome;
    private String atributo;
    private Nivel nivel;
    private String familia;
    private String imagem;

    public Digimon() {
    }

    public Digimon(String imagem, String familia, Nivel nivel, String atributo, String nome, Integer id) {
        this.imagem = imagem;
        this.familia = familia;
        this.nivel = nivel;
        this.atributo = atributo;
        this.nome = nome;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}