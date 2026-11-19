package entities;

import java.math.BigDecimal;

interface ProdutoFactory {

    public Produto criarProduto(String nome, BigDecimal preco, String descricao);
}
