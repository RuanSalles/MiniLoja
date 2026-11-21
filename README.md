# Mini Loja

Projeto de uma **Mini Loja** executada no **terminal (CLI)**, com **menu interativo**, construída em **Java puro** (sem frameworks), evoluindo aos poucos para consolidar fundamentos da linguagem e boas práticas.

## Intuito do projeto

Construir uma Mini Loja com módulos de:
- **Cadastro** (ex.: produtos)
- **Estoque**
- **Vendas (carrinho de compra)**
- Posteriormente **recibo de venda** e validação de transações

Tudo rodando via terminal, com foco em aprendizado prático. Como desenvolvedor **pleno em PHP**, este projeto também serve para **migrar para a stack Java**, fazendo paralelos entre estruturas e hábitos das duas linguagens.

## Stack e restrições (resumo)

- **Java puro**, sem Spring/Hibernate/Lombok/frameworks no início
- Execução via **terminal/CLI**
- Evolução incremental conforme planejamento do repositório

## Decisão técnica (Etapa 0 / planejamento)

Por decisão técnica e aderência às orientações do planejamento, **classes adicionais criadas anteriormente foram removidas por hora** (incluindo hierarquia/fábricas/seletores associados ao padrão **Factory Method**). A modelagem atual segue com a classe **`Produto` concreta** e a categoria representada por **`enum`** (`ProdutoCategoria`), até surgirem diferenças reais de atributos/comportamento que justifiquem outra estrutura.

- A implementação anterior baseada em **Factory Method foi revisada** e, por não agregar valor ao cenário atual, foi **removida**, mantendo o foco na modelagem simples definida no planejamento.
- **Não será criada nova branch** nesta etapa (conforme alinhado), seguindo o fluxo atual do repositório.

## O que aprendi na Etapa 0 (paralelo PHP → Java)

- Em **PHP**, é comum validar a ausência/“vazio” de variáveis com verificações como **`!$var`** (dependendo do contexto, checando falsy/nulidade de forma implícita). Em **Java**, a validação é mais explícita e tipada: por exemplo, uso de **`Objects.requireNonNull(...)`** para **não aceitar valores nulos** e falhar cedo com uma mensagem clara.
- Também trabalhei a ideia de **normalizar entrada**: ao receber texto, aplicar **`trim()`** e rejeitar conteúdo em branco (ex.: nome vazio após remover espaços), em vez de confiar em checagens frouxas.
- Em conjunto com validações de domínio (tamanho/regra de negócio), isso aproxima o hábito de “garantir estado válido no momento da criação/entrada”, fazendo um paralelo direto com as guard clauses que já uso em PHP, mas agora com APIs e semântica do Java (`null` explícito, exceções de domínio, tipos).

## Estado atual — Etapa 1 (em andamento)

A Etapa 1 do planejamento (CRUD de produtos com repositório em memória) foi **iniciada**. A estrutura em camadas já está montada e o **cadastro de produtos funciona pelo terminal**; as demais operações do CRUD ainda não foram expostas ao usuário.

**Aderência ao planejamento:** aproximadamente **71%** da Etapa 1 (contrato e repositório em memória prontos; serviço e menu parciais). Detalhamento em `CHECKLIST_ETAPA1_VALIDACAO.md`.

### O que já foi implementado

| Camada | Arquivo | Situação |
|---|---|---|
| Domínio | `src/main/java/domain/entities/Produto.java` | Entidade concreta, `UUID`, validações e `BigDecimal` (Etapa 0) |
| Domínio | `src/main/java/domain/enums/ProdutoCategoria.java` | `enum` com `ELETRONICO`, `FERRAMENTA`, `VESTUARIO` |
| Domínio | `src/main/java/domain/exceptions/` | Exceções de domínio para argumentos, quantidade e valor |
| Infraestrutura | `src/main/java/infra/repositories/ProdutoRepository.java` | Contrato com `salvar`, `buscarPorId`, `buscarTodos`, `atualizar`, `excluir` |
| Infraestrutura | `src/main/java/infra/repositories/ProdutoRepositoryInMemory.java` | Implementação com `Map<UUID, Produto>`: salvar, buscar, listar, atualizar e excluir |
| Aplicação | `src/main/java/application/ProdutoService.java` | Criado; por enquanto somente `cadastrarProduto(...)` |
| Apresentação | `src/main/java/presentation/Menu.java` | Menu principal com acesso ao módulo de produtos |
| Apresentação | `src/main/java/presentation/ProdutoMenu.java` | Menu de produtos; opção `1 - Cadastrar Produto` funcional |
| Entrada | `src/main/java/Main.java` | Monta repositório → serviço → menus → execução |

### O que ainda falta para concluir a Etapa 1

- Expor `buscarPorId`, `listarTodos`, `atualizar` e `excluir` no `ProdutoService` e implementar as opções `2` a `5` do `ProdutoMenu`.
- Fazer `ProdutoService` depender da interface `ProdutoRepository` (e não de `ProdutoRepositoryInMemory`).
- Exibir o `UUID` na listagem para permitir buscar, atualizar e excluir pelo terminal.
- Tratar entradas inválidas (opções não numéricas, preço inválido, categoria/UUID inválidos) e IDs inexistentes com mensagens amigáveis, sem stack trace.
- Adicionar `0 - Sair` ao menu principal e testar manualmente todos os fluxos do CRUD.

## Como executar

Requisito: **Java 21** e **Maven** instalados.

```bash
cd /home/sales/vhost/MiniLoja
mvn -q -DskipTests compile
java -cp target/classes Main
```

Roteiro de teste do fluxo disponível hoje (cadastro):

```
1            # Produtos
1            # Cadastrar Produto
Teclado
150.50
Descricao teste
ELETRONICO
0            # Voltar
0            # Sair do menu principal
```

## Validação e documentação

- Etapa 0: `CHECKLIST_ETAPA0_VALIDACAO.md`
- Etapa 1: `CHECKLIST_ETAPA1_VALIDACAO.md` (aderência, itens marcados, evidências de build/execução e melhorias priorizadas)
- Planejamento completo: `Planejamento-MiniLoja-Java.md`

O checklist da Etapa 1 registra o que foi realizado, o que está parcial, as evidências de compilação/execução e as melhorias recomendadas para atingir o critério de conclusão: “o CRUD funciona pelo terminal e as regras não dependem da implementação em memória”.
