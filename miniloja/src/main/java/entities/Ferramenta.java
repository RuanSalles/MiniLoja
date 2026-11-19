package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class Ferramenta extends Produto{


    public Ferramenta(String nome, BigDecimal preco, ProdutoCategoria categoria, String descricao) {
        super(nome, preco, categoria, descricao);
    }
}
