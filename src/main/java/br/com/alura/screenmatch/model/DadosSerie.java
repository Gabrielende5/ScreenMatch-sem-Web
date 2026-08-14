package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)//Ignora o que não se sabbe, não tenta converter. Por padrão vem false
public record DadosSerie(@JsonAlias("Title") String titulo,@JsonAlias("totalSeasons") Integer totalTemporadas,@JsonAlias("imdbRating") String avaliacao, //Json Alias: Serve apenas para ler o json, ou seja, irá ler o "title", dai na hora de escrever um json, ele vai usar o nome original do atribbulo "titulo"
                                                                                                                                                            //Tem como colocar um range de nomes além de "Title" para titulo, como exemplo "({"Title", "Titulo"})" para caso se use outra API que ao invés de "Title" se tenha "Titulo" e não se precise mudar para um específico (pode se ter os dois)
                            @JsonProperty("imdbVotes") String votos) { //JsonProperty: Na hora que gerar um json com esses dados, ele vai colocar no json o nome "imdbVotes".
                                                                           // Tenta ler o "imdVotes" e quando for escrever, escreve "imdbVotes"
}
