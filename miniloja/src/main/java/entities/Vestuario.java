package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class Vestuario extends Produto{

    public Vestuario(String nome, BigDecimal preco, ProdutoCategoria categoria, String descricao) {
        super(nome, preco, categoria, descricao);
    }
}
