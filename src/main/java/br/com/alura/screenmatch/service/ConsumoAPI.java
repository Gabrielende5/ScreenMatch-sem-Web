package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obterDados(String endereco){ //Basicamente é o get das requisições http
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> respose = null; //Inicia zero
        try {
            respose=cliente.send(request,HttpResponse.BodyHandlers.ofString()); //Tenta obter o que for requisitado
        } catch (IOException e){
            throw  new RuntimeException(e);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        String json = respose.body(); // Se a tentativa deu certo, vai mostrar o json, se não, vai mostral null (pois foi iniciado com null)
        return json;
    }
}
