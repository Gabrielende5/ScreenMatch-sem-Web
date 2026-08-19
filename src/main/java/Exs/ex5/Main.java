package Exs.ex5;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);
        numeros.replaceAll(n -> n * 3); //Substituiu todos os elementos por *3
        System.out.println(numeros);
    }
}
