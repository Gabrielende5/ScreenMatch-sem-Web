package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)//Ignora o que não se sabbe, não tenta converter. Por padrão vem false

public record DadosEpisodio(@JsonAlias("Title") String titulo, @JsonAlias("Episode") Integer numeroEpisodio, @JsonAlias("imdbRating") String avaliacao,@JsonAlias("Released") String dataLancamento) {
}
