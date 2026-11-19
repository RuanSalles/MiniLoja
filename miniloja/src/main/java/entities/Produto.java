package entities;

import enums.ProdutoCategoria;

import java.math.BigDecimal;

public abstract class Produto {
    private String nome;
    private BigDecimal preco;
    private ProdutoCategoria categoria;
    private String descricao;
    private Integer quantidade;

    public Produto(String nome, BigDecimal preco, ProdutoCategoria categoria, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.descricao = descricao;
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

    public void adicionarQuantidade(Integer quantidade) {
        if(quantidade < 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        this.quantidade += quantidade;
    }

    public void removerQuantidade(Integer quantidade) {
        if(this.quantidade <= 0 || quantidade > this.quantidade) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.quantidade -= quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco= " + preco +
                ", categoria= " + categoria +
                ", quantidade= " + quantidade +
                '}';
    }
}
