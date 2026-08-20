package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScreenmatchApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }
    @Override
        //É um função da interface implementada "CommandLineRunner"
    public void run(String... args) throws Exception { //Aqui que será o "main" de quando executar o código normal, pois se está usando o Spring Framework
        Principal principal = new Principal();
        principal.exibirMenu();


    }
}
