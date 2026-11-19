package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class Vestuario extends Produto{

    public Vestuario(String nome, BigDecimal preco, String descricao) {
        super(nome, preco, ProdutoCategoria.VESTUARIO, descricao);
    }
}
