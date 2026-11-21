package presentation;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private ProdutoMenu produtoMenu;

    public Menu(Scanner scanner, ProdutoMenu produtoMenu) {
        this.scanner = scanner;
        this.produtoMenu = produtoMenu;
    }

    public void executar() {
        int opcao;

        do {
            System.out.println("\n====MINILOJA====");
            System.out.println("1 - Produtos");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: produtoMenu.executar();
            }

        } while (opcao != 0);
    }
}
