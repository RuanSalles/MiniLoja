package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class Ferramenta extends Produto{


    public Ferramenta(String nome, BigDecimal preco, String descricao) {
        super(nome, preco, ProdutoCategoria.FERRAMENTA, descricao);
    }
}
