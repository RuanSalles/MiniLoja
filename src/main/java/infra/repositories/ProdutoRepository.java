package infra.repositories;

import domain.entities.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository {
    void salvar(Produto produto);
    Optional<Produto> buscarPorId(UUID id);
    List<Produto> buscarTodos();
    void atualizar(Produto produto);
    void excluir(Produto produto);
}
