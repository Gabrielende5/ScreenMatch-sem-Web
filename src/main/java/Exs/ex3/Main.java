package Exs.ex3;

public class Main {
    public static void main(String[] args) {
        Conversor conversor = palavra -> palavra.toUpperCase();
        System.out.println(conversor.converter("java"));
    }
}
