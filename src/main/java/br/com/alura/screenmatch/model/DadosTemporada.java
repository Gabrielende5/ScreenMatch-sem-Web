package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)//Ignora o que não se sabbe, não tenta converter. Por padrão vem false

public record DadosTemporada(@JsonAlias("Season") Integer numero,@JsonAlias("Episodes") List<DadosEpisodio> episodios) {
}
