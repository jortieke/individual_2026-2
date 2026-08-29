package sptech.school.digimon;

public enum Nivel {
    Treinamento(1, "Treinamento"),
    Criança(2, "Criança"),
    Adulto(3, "Adulto"),
    Perfeito(4, "Perfeito"),
    Mega(5, "Mega"),
    Jogress(6, "Jogress"),
    Armadura(7, "Armadura");

    private final Integer nivel;
    private final String nome;

    Nivel(Integer nivel, String nome) {
        this.nivel = nivel;
        this.nome = nome;
    }

    public Integer getNivel() {
        return nivel;
    }

    public String getNome(){
        return nome;
    }
}
