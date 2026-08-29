package sptech.school.digimon;

import java.time.LocalDate;

public class Digimon {
    private Integer id;
    private String nome;
    private Integer poder; // Novo campo
    private String atributo;
    private Nivel nivel;
    private String familia;
    private String imagem;
    private LocalDate dataRegistro; // Novo campo

    public Digimon() {
    }

    public Digimon(Integer id, String nome, Integer poder, String atributo, Nivel nivel, String familia, String imagem, LocalDate dataRegistro) {
        this.id = id;
        this.nome = nome;
        this.poder = poder;
        this.atributo = atributo;
        this.nivel = nivel;
        this.familia = familia;
        this.imagem = imagem;
        this.dataRegistro = dataRegistro;
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

    public Integer getPoder() {
        return poder;
    }

    public void setPoder(Integer poder) {
        this.poder = poder;
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

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}