package presentation;

import application.ProdutoService;
import domain.entities.Produto;
import domain.enums.ProdutoCategoria;

import java.math.BigDecimal;
import java.util.Scanner;

public class ProdutoMenu {
    private final Scanner scanner;
    private ProdutoService produtoService;

    public ProdutoMenu(Scanner scanner, ProdutoService produtoService) {
        this.scanner = scanner;
        this.produtoService = produtoService;
    }

    public void executar() {
        int opcao;

        do {
            System.out.println("\n====PRODUTOS====");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Buscar Produto");
            System.out.println("4 - Atualizar Produto");
            System.out.println("5 - Excluir Produto");
            System.out.println("0 - Voltar");

            System.out.println("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrar();
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("Nome: ");
        String nome = scanner.nextLine();

        System.out.println("Preço: ");
        BigDecimal preco = new BigDecimal(scanner.nextLine());

        System.out.println("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.println("Categorias Disponíveis:");

        for (ProdutoCategoria categoria : ProdutoCategoria.values()) {
            System.out.println(categoria);
        }

        System.out.println("Categoria: ");
        ProdutoCategoria categoria = ProdutoCategoria.valueOf(scanner.nextLine().toUpperCase());

        Produto produto = produtoService.cadastrarProduto(nome, preco, categoria, descricao);

        System.out.println("Produto cadastrado com sucesso!");
        System.out.println(produto);
    }
}
