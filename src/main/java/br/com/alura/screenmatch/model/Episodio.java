package br.com.alura.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {
    private Integer temporada;  //Usado "Integer" ao invés de "int" por conta que "Integer" consegue guardar valores null, já no "int" dará erro diretamente. No final esse null será filtrado, mas é bom que não de um erro só por isso
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento; //No "DadosEpisodio" é usado String, mas aqui para aprender como usar LocalDate, vai usar LocalDate
    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio){
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numeroEpisodio();
        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());  //Já que "avaliacao" é um "Double", ou seja, um objeto double, precisa ser valufOf, já se fosse double primitivo ("double"), um parseDouble seria suficiente, porém teria como fazer valufOf que com double primito que não daria problema (o contrário que daria)
        } catch (NumberFormatException e){
            this.avaliacao=0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento()); //Tentar converter a string para LocalDate, sendo que a String precisa estar no padrão do LocalDate (ano-mes-dia), se não tiver, dará erro que precisará ser tratado
        } catch (DateTimeException e){
            this.dataLancamento=null;
        }

    }
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento
                ;
    }
}
