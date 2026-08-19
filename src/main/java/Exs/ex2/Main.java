package Exs.ex2;

public class Main {
    public static void main(String[] args) {
        Primo primo = numero -> {
            if (numero <= 1) return false;
            for (int i = 2; i <= Math.sqrt(numero); i++) {
                if (numero % i == 0) {
                    return false;
                }
            }
            return true;
        };
        System.out.println(primo.verificacao(11));
        System.out.println(primo.verificacao(12));
    }
}
