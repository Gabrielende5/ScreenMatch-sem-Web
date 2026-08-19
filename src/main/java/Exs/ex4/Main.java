package Exs.ex4;
//Verificação Palindromo
public class Main {
    public static void main(String[] args) {
        Verificador verificador = palavra -> {
            StringBuilder sb = new StringBuilder(palavra);
            if (sb.substring(0, sb.length() / 2).equals(sb.reverse().substring(0, sb.length() / 2))) {
                return true;
            } else {
                return false;
            }
        };
        System.out.println(verificador.verificacao("radar"));
        System.out.println(verificador.verificacao("java"));
    }
}
