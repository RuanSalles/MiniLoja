package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class Eletronico extends Produto{

    public Eletronico(String nome, BigDecimal preco, String descricao) {
        super(nome, preco, ProdutoCategoria.ELETRONICO, descricao);
    }
}
