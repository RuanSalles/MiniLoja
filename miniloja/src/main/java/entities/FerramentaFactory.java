package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class FerramentaFactory implements ProdutoFactory{
    @Override
    public Produto criarProduto(String nome, BigDecimal preco, String descricao) {
        return new Ferramenta(nome, preco, ProdutoCategoria.FERRAMENTA, descricao);
    }
}
