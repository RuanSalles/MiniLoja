package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public class VestuarioFactory implements ProdutoFactory{
    @Override
    public Produto criarProduto(String nome, BigDecimal preco, String descricao) {
        return new Vestuario(nome, preco, ProdutoCategoria.VESTUARIO, descricao);
    }
}
