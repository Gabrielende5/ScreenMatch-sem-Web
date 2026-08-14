package br.com.alura.screenmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
        //"<T>" é para quando não se sabe qual vai ser entidade devolvida, mas se sabe que alguma coisa será devolvida
            //É um generic, no estilo do "var"
}
