package Exs.ex12;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> listaDeNumeros = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(5, 6, 7, 8),
                Arrays.asList(9, 10, 11, 12)
        );
        List<Integer> novaLista = listaDeNumeros.stream()
                .flatMap(l -> l.stream()
                        .filter(n->{ //Verificação se o numero dessa lista é primo:
                            if (n <= 1) {
                                return false;
                            }

                            for (int i = 2; i < n; i++) {
                                if (n % i == 0) {
                                    return false; // encontrou divisor, não é primo
                                }
                            }

                            return true; // não encontrou nenhum divisor, é primo
                        }))
                .sorted()
                .collect(Collectors.toList());
        System.out.println(novaLista);
    }
}
