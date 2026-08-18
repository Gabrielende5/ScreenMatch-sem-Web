package Ex.ex1;

public class Main {
    public static void main(String[] args) {
        Multiplicador multiplica = (a,b)->a*b; //Dentro dos parenteses é as variáveis e depois do "->" é o que a função obrigatoria da interface "Multiplicador" irá retornar
        System.out.println(multiplica.multiplicacao(5,3));
    }
}
