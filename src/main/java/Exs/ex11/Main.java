package Exs.ex11;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> palavras = Arrays.asList("apple", "banana", "apple", "orange", "banana");
        palavras.stream()
                .distinct()//Filtra dados duplicados (Um HashSet)
                .forEach(System.out::println);
    }
}
