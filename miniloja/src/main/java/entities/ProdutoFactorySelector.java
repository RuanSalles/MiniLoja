package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Objects;

public class ProdutoFactorySelector {
    private static final EnumMap<ProdutoCategoria, ProdutoFactory> produtoFactoryMap = new EnumMap<>(ProdutoCategoria.class);

    static {
        produtoFactoryMap.put(ProdutoCategoria.ELETRONICO, new EletronicoFactory());
        produtoFactoryMap.put(ProdutoCategoria.FERRAMENTA, new FerramentaFactory());
        produtoFactoryMap.put(ProdutoCategoria.VESTUARIO, new VestuarioFactory());
    }

    public static Produto criarProduto(ProdutoCategoria categoria, String nome, BigDecimal preco, String descricao) {
        Objects.requireNonNull(categoria, "Categoria não informada");
        ProdutoFactory factory = produtoFactoryMap.get(categoria);
        if (factory == null) {
            throw new IllegalArgumentException("Fábrica não encontrada para a categoria: " + categoria);
        }
        return factory.criarProduto(nome, preco, descricao);
    }
}

