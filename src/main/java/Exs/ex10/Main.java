package Exs.ex10;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> novaLista = numeros.stream()
                .filter(n->n%2!=0)
                .map(n->n*2)
                .collect(Collectors.toList());
        System.out.println(novaLista);
    }
}
