package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class EletronicoFactory implements ProdutoFactory{
    @Override
    public Produto criarProduto(String nome, BigDecimal preco, String descricao) {
        return new Eletronico(nome, preco, ProdutoCategoria.ELETRONICO, descricao);
    }
}
