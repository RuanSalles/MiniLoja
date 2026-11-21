package infra.repositories;

import domain.entities.Produto;

import java.util.*;

public class ProdutoRepositoryInMemory implements ProdutoRepository {
    private final Map<UUID, Produto> produtos = new HashMap<>();

    @Override
    public void salvar(Produto produto) {
        produtos.put(produto.getId(), produto);
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return Optional.ofNullable(produtos.get(id));
    }

    @Override
    public List<Produto> buscarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public void atualizar(Produto produto) {
        if(!produtos.containsKey(produto.getId())) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        produtos.put(produto.getId(), produto);
    }

    @Override
    public void excluir(Produto produto) {
        if(!produtos.containsKey(produto.getId())) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        produtos.remove(produto.getId());
    }
}
