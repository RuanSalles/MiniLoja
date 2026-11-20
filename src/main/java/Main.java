import domain.entities.Produto;
import domain.enums.ProdutoCategoria;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        Produto produto = new Produto("Martelo", new BigDecimal("-50.00"), ProdutoCategoria.ELETRONICO, "Martelo de aço");
        System.out.println(produto);

    }
}
