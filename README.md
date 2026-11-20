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
