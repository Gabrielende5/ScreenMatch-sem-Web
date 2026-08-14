package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

public class ScreenmatchApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
        //É um função da interface implementada "CommandLineRunner"
    public void run(String... args) throws Exception { //Aqui que será o "main" de quando executar o código normal, pois se está usando o Spring Framework
        ConsumoAPI consumoAPI = new ConsumoAPI();
        var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=B8687C09");
            //"var" é um tipo variável que se adapta com o que for retornado, podendo ser String, int, double, ...
                //Não é recomendável em programação competitiva (maratona), pois para se confundir qual o tipo da variável é coisa de 3 segundos, mas em POO é bom
        System.out.println(json);
        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json,DadosSerie.class); //".class" por conta que no obter dados se é "Class"
        System.out.println(dados);
    }
}
