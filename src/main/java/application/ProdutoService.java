package application;

import domain.entities.Produto;
import domain.enums.ProdutoCategoria;
import infra.repositories.ProdutoRepositoryInMemory;

import java.math.BigDecimal;

public class ProdutoService {
    ProdutoRepositoryInMemory produtoRepository;

    public ProdutoService(ProdutoRepositoryInMemory produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrarProduto(String nome, BigDecimal preco, ProdutoCategoria categoria, String descricao) {
        Produto produto = new Produto(nome, preco, categoria, descricao);
        produtoRepository.salvar(produto);

        return produto;
    }
}
