package domain.entities;

import domain.enums.ProdutoCategoria;
import domain.exceptions.InvalidProductArgumentException;
import domain.exceptions.InvalidProductQuantityException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Produto {
    private final UUID id;
    private String nome;
    private BigDecimal preco;
    private ProdutoCategoria categoria;
    private String descricao;
    private Integer quantidade;

    public Produto(String nome, BigDecimal preco, ProdutoCategoria categoria, String descricao) {
        // Motivação: impedir nome nulo/vazio/espacos; identidade de exibição não pode ser em branco.
        if (nome == null || nome.isBlank()) {
            throw new InvalidProductArgumentException("Nome do produto não pode ser nulo ou vazio");
        }
        // Motivação: preco obrigatório e monetário; rejeita nulo e evita valor negativo (regra de domínio inicial).
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductArgumentException("Preço do produto não pode ser nulo ou negativo");
        }
        // Motivação: categoria define classificação obrigatória do produto (enum), não pode ser ausente.
        this.categoria = Objects.requireNonNull(categoria, "Categoria do produto não pode ser nula");
        // Motivação: descricao é campo obrigatório na modelagem atual; evita estado nulo em saídas/serialização simples.
        this.descricao = Objects.requireNonNull(descricao, "Descrição do produto não pode ser nula");
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.preco = preco;
        this.quantidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public ProdutoCategoria getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public UUID getId() {
        return id;
    }

    public void adicionarQuantidade(Integer quantidade) {
        // Motivação: quantidade informada deve existir e ser >= 0; evita NullPointerException e entradas inválidas.
        Objects.requireNonNull(quantidade, "Quantidade não pode ser nula");
        if (quantidade.compareTo(0) < 0) {
            throw new InvalidProductQuantityException("Quantidade inválida");
        }

        this.quantidade += quantidade;
    }

    public void removerQuantidade(Integer quantidade) {
        // Motivação: mesma validação de entrada para operações simétricas e mensagens consistentes.
        Objects.requireNonNull(quantidade, "Quantidade não pode ser nula");
        if (quantidade.compareTo(0) < 0) {
            throw new InvalidProductQuantityException("Quantidade inválida");
        }

        // Motivação: impedir remover mais do que existe ou de estoque já zerado (invariante de estoque não negativo).
        if (this.quantidade.compareTo(0) <= 0 || quantidade.compareTo(this.quantidade) > 0) {
            throw new InvalidProductQuantityException("Não é possível remover quantidade maior que a disponível");
        }
        this.quantidade -= quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "ID: " + id +
                ", nome: '" + nome + '\'' +
                ", preco: " + preco +
                ", categoria: " + categoria +
                ", quantidade: " + quantidade +
                ", descrição: " + descricao +
                '}';
    }
}
