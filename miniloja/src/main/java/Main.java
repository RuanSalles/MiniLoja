import entities.Produto;
import entities.ProdutoFactorySelector;
import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        Produto produto = new ProdutoFactorySelector()
                .criarProduto(ProdutoCategoria.FERRAMENTA, "Martelo", new BigDecimal("10.00"), "Martelo de madeira");
        System.out.println(produto);

    }
}
