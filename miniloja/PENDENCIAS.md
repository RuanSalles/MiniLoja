# Pendências — MiniLoja (Factory Method)

## Estado atual (já concluído)

- `Produto` abstrata e subclasses `Eletronico`, `Ferramenta`, `Vestuario` com categoria fixa no `super(...)`.
- `ProdutoFactory` e três implementações concretas (`EletronicoFactory`, `FerramentaFactory`, `VestuarioFactory`).
- `ProdutoFactorySelector` com `EnumMap`, validação de categoria nula e delegação à fábrica.
- `Main` cria um produto via seletor; `mvn compile` e `java -cp target/classes Main` passaram.

## Antes de começar

- [ ] Reexecutar `mvn compile && java -cp target/classes Main`; saída esperada contendo `categoria= FERRAMENTA, quantidade= 0`.

## Prioridade alta — comportamento

- [ ] `src/main/java/entities/Produto.java:42-55` — validar parâmetros `null` e corrigir `removerQuantidade`: hoje `removerQuantidade(-5)` **aumenta** o estoque, e `adicionarQuantidade(null)` / `removerQuantidade(null)` lançam `NullPointerException` no unboxing. Dica: `Objects.requireNonNull` e uma condição que cubra quantidade `<= 0` e maior que o estoque.
- [ ] `src/main/java/Main.java:10-11` — chamar `ProdutoFactorySelector.criarProduto(...)` diretamente, sem `new`; o seletor só tem membros estáticos, então adicione um construtor `private` nele para impedir instanciação.

## Prioridade média — desenho

- [ ] `src/main/java/entities/ProdutoFactory.java:5-7` — a interface é package-private e o método declara `public` de forma redundante. Estude quando faz sentido torná-la pública (somente se um cliente fora de `entities` precisar usá-la diretamente).
- [ ] `src/main/java/entities/Produto.java:14` — o construtor da classe abstrata é `public` e ainda recebe `categoria`. Agora que as subclasses fixam a categoria, avalie torná-lo `protected`.
- [ ] `src/main/java/entities/ProdutoFactorySelector.java:10` — convenção de nome: constante `static final` em maiúsculas. O mapa é privado, mas mutável: estude `Collections.unmodifiableMap` (nesse caso o campo passa a ser `Map`) e a validação fail-fast com `EnumSet.allOf(ProdutoCategoria.class)` para detectar uma categoria nova sem factory.
- [ ] `src/main/java/entities/ProdutoFactorySelector.java:21-23` — factory ausente gera `IllegalArgumentException`; discuta se `IllegalStateException` seria mais adequado, já que seria problema de configuração.

## Prioridade baixa — acabamento

- [ ] `src/main/java/entities/Produto.java:58-64` — `toString` não inclui `descricao`.
- [ ] Validar entradas: `nome` nulo/vazio, `preco` nulo/negativo, `descricao` nula. Decida onde fica a validação: na fábrica (fronteira de criação) ou na entidade (invariantes do objeto).
- [ ] `src/main/java/entities/Produto.java:8-11` — os campos `nome`, `preco`, `categoria` e `descricao` podem ser `final`; `quantidade` não pode, pois muda.
- [ ] Estilo: espaçamento em `class Eletronico extends Produto{` (e equivalentes) e linha em branco extra em `Ferramenta.java:9`.

## Exercícios de estudo

1. Adicione `LIVRO` ao enum, crie `Livro` e `LivroFactory` e registre a entrada no `EnumMap`. Conte quantos arquivos mudaram e relacione com o princípio Aberto/Fechado: o seletor ainda precisa ser editado.
2. Substitua temporariamente o `EnumMap` por um `switch` expression e compare as duas abordagens (Simple Factory x Factory Method).
3. Teste manualmente os três cenários: criação nas três categorias, categoria `null` e remoção acima do estoque. Se quiser automatizar, será necessário adicionar uma dependência de teste ao `pom.xml` (hoje ele não declara nenhuma).

## Comandos úteis

```bash
mvn compile
java -cp target/classes Main
```
