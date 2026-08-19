package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    //"https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=B8687C09"
    private final String ENDERECO = "https://www.omdbapi.com/?t="; //"final" é uma variável que nunca será modificado
                                                                     //Boa prática é colocar o nome da variável "final" como tudo maiúsculo
    private final String API_KEY = "&apikey=B8687C09";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    public void exibirMenu(){
        System.out.print("Digite o nome da série para busca: ");

        String serie = leitura.nextLine();
        ConsumoAPI consumoAPI = new ConsumoAPI();
        var json = consumoAPI.obterDados(ENDERECO+serie.replace(" ","+")+API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json,DadosSerie.class); //".class" por conta que no obter dados se é "Class"
        System.out.println(dadosSerie);

        List<DadosTemporada> listaTemporadas = new ArrayList<>();

        for (int i = 1; i <=dadosSerie.totalTemporadas() ; i++) {//Não tem temporada "0"
            json = consumoAPI.obterDados(ENDERECO+serie.replace(" ","+")+ "&season=" + i +API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            listaTemporadas.add(dadosTemporada);
        }
        listaTemporadas.forEach(System.out::println); //É mostrar todos os elementos da lista "listaTemporadas" linha por linha (é a mesma coisa que um for)
            //É a mesma coisa que:
                //listaTemporadas.forEach(t->System.out.println(t))
//        for (int i = 0; i < dadosSerie.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = listaTemporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
                //Simplificando com lambdas:
        listaTemporadas.forEach(t->t.episodios().forEach(e-> System.out.println(e.titulo())));

        //Streams
//        List<String> nomes = Arrays.asList("Jacque","Iasmin","Paulo","Rodrigo","Nico");
//        nomes.stream()
//                .sorted() //Operação intermediária -> geram novos fluxos de dados para se fazer funções agregadas; podem ser aplicadas em uma stream e retornam uma nova stream como resultado
//                .limit(3) //Operação intermediária
//                .filter(n->n.startsWith("N")) //Operação intermediária
//                .map(n->n.toUpperCase()) //Operação intermediária
//                                                    //"map" ->  permite transformar cada elemento da stream em outro tipo de dado
//                .forEach(System.out::println); //Operação final -> aquelas que finalizam (ex: coletar para uma outra lista, imprimir e etc); encerram a stream e retornam um resultado concreto

        List<DadosEpisodio> dadosEpisodiosGeral = listaTemporadas.stream()
                .flatMap(t->t.episodios().stream()) //Faz com que seja possível aglutinar todos os episódios de todas as temporadas em apenas uma lista
                .collect(Collectors.toList());//Taca para a lista o que foi feito na operação intermediária. É a operação final
                                                //Teria como trocar tudo isso (collect...) por ".toList()", porém a lista criada seria imutável, ou seja, não teria como adicionar algo (um novo episódio) depois na lista "dadosEpisodiosGeral"

                            //Top 5 episódios de todas as temporadas
        dadosEpisodiosGeral.stream()
                .filter(e->!(e.avaliacao().equalsIgnoreCase("N/A")))//Filtrar para que quando o episódio tiver a avaliação igual (equals) a "N/A", se irá não colocar ele (!) na lista
                                                                                                //O "IgnoreCase" no "equals" é apenas para analisar mesmo se for "N/A" ou "n/a"
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) //Ordenar pela "avaliacao" em ordem reversa
                .limit(5)//Top 5
                .forEach(System.out::println);

        List<Episodio> episodios = listaTemporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(d->new Episodio(t.numero(),d)) //Para cada DadosEpisodio de cada DadosTemporada, criar um novo "Episodio"
                                                                            //É bom passar o "d", ou seja, o DadosEpisodio para se conseguir pegar o titulo, numero do episódio e etc sem precisa passar vários atributos para o contrutor
                ).collect(Collectors.toList());
        episodios.forEach(System.out::println);

        System.out.print("A partir de que ano você deseja ver os episódios: ");
        int ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano,1,1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Formato brasileiro de dia/mes/ano
        episodios.stream()
                .filter(e ->e.getDataLancamento()!=null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() + " Episódio: " + e.getTitulo() + " DataLançamento: " + e.getDataLancamento().format(formatador)
                ));

    }
}

