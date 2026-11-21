import application.ProdutoService;
import domain.entities.Produto;
import domain.enums.ProdutoCategoria;
import infra.repositories.ProdutoRepositoryInMemory;
import presentation.Menu;
import presentation.ProdutoMenu;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ProdutoRepositoryInMemory produtoRepository = new ProdutoRepositoryInMemory();
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        Scanner scanner = new Scanner(System.in);

        ProdutoMenu produtoMenu = new ProdutoMenu(scanner, produtoService);
        Menu menuPrincipal = new Menu(scanner, produtoMenu);

        menuPrincipal.executar();

    }
}
