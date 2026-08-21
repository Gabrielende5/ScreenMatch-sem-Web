package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
//        System.out.println();
//        System.out.println("Top 5 episódios de todas as temporadas");
//        dadosEpisodiosGeral.stream()
//                .filter(e->!(e.avaliacao().equalsIgnoreCase("N/A")))//Filtrar para que quando o episódio tiver a avaliação igual (equals) a "N/A", se irá não colocar ele (!) na lista
//                                                                                                //O "IgnoreCase" no "equals" é apenas para analisar mesmo se for "N/A" ou "n/a"
//                .peek(e-> System.out.println("Primeiro filtro (N/A) " + e)) //Usado para "debugar", ou seja, ver se está correto
//                                                                                            //Tipo dar vários prints para cada iteração de um for
//                                                                                                //Ao ver o "peek", se dá para ver que o "Streams" faz da forma que ele acha mais otimizada (por exemplo, ele filtra tudo primeiro, depois ordena um específico, depois pega um específico para o limite, depois mapeia esse um específico e só depois imprime esse um em específico. Já para o próximo "e" (episódio), ele apenas ordena-limita-mapeia e depois mostra, pois filtragem já foi tudo no início
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) //Ordenar pela "avaliacao" em ordem reversa
//                .peek(e-> System.out.println("Ordenação " + e))
//                .limit(5)//Top 5
//                .peek(e-> System.out.println("Limite " + e))
//                .map(e->e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = listaTemporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(d->new Episodio(t.numero(),d)) //Para cada DadosEpisodio de cada DadosTemporada, criar um novo "Episodio"
                                                                            //É bom passar o "d", ou seja, o DadosEpisodio para se conseguir pegar o titulo, numero do episódio e etc sem precisa passar vários atributos para o contrutor
                ).collect(Collectors.toList());
        episodios.forEach(System.out::println);

        System.out.print("Digite um trecho do título do episódio: ");
        String trechoTitulo = leitura.nextLine();


                //Fazer meio que uma busca para tentar procurar o título com base em um trecho, retornando apenas o primeiro que achar (sempre irá retornar o primeiro que achar pelo findFirst) -> para retornar todos, é só criar uma lista ao invés de apenas um valor
                                                                                                                                                                                                        //"List<T>" quando você espera zero ou mais resultados
                                                                                                                                                                                                        //"Optional<T>" quando você espera zero ou um resultado
        Optional<Episodio> episodioBuscado = episodios.stream() //Optional é um objeto contêiner que pode ou não conter um valor nulo (valor nulo = não tem episódio com esse trecho, uma facilidade que o Optional proporciona)
                                                                    //os "id_s" muitas das vezes usam o Optional, pois você pode achar o id, ou pode não achar (null)
                .filter(e-> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst(); //Devolve resultado mais lento que o "findAny()", todavia, o "findFirst()" tem garantia que sempre irá devolver o primeiro em uma ordem específica, aumentando assima a "acurácia", já o "findAny()" ele procura e o que ele achar primeiro (qualquer um),
                                //É uma operação final, que nem o forEach(print...)
                                //"findAny()": utilizado para encontrar qualquer elemento que satisfaça uma determinada condição em uma coleção, onde cada thread pode buscar um elemento da coleção de forma paralela (mais rápido), atividades paralelas



        if(episodioBuscado.isPresent()){ //se "episódioBuscado" tem uma referencia de "episódio" no Optional, está presente (não é nulo, não é Empty):
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get());//o "get()" pega o episódio que está lá dentro (não tem como ser null o resultado, pois tem o isPresent() na verificação)
        } else{
            System.out.println("Episódio não encontrado!");
        }

//        System.out.print("A partir de que ano você deseja ver os episódios: ");
//        int ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Formato brasileiro de dia/mes/ano
//        episodios.stream()
//                .filter(e ->e.getDataLancamento()!=null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() + " Episódio: " + e.getTitulo() + " DataLançamento: " + e.getDataLancamento().format(formatador)
//                ));

                    //Um dicionario, aonde a chave é a temporada e o valor é a média da avaliação da temporada
        Map<Integer,Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e->e.getAvaliacao()>0.0)//Filtra para ter episódios apenas com avaliação > 0.0, ou seja, aqueles N/A (no constructor foi definido que N/A = 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,Collectors.averagingDouble(Episodio::getAvaliacao))); //"groupingBy(chave,valor)" é o método de guardar em um dicionario usando stream()
        System.out.println(avaliacoesPorTemporada);

                    //Gera estatítiscas básicas da série (todos os episódios de todas as temporadas)
        DoubleSummaryStatistics estatisticas = episodios.stream()
                .filter(e-> e.getAvaliacao()>0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao)); //"Episodio::getAvaliacao" = "e -> e.getAvaliacao()"
        System.out.println("Média: " + estatisticas.getAverage());
        System.out.println("Melhor episódio: " + estatisticas.getMax());
        System.out.println("Pior episódio: " + estatisticas.getMin());
        System.out.println("Quantidade de episódios avaliados: "+ estatisticas.getCount());
    }
}

