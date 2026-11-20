# MiniLoja --- Planejamento de Desenvolvimento

> **Objetivo:** construir uma aplicação de loja executada pelo terminal,
> utilizando Java puro (sem frameworks inicialmente), para consolidar
> fundamentos da linguagem, orientação a objetos, estruturas de dados,
> persistência, testes e aplicação consciente de padrões de projeto.

Este documento funciona como roteiro de desenvolvimento. Marque cada
item conforme concluir e atualize as decisões quando o projeto evoluir.

------------------------------------------------------------------------

## 1. Visão geral

A MiniLoja será um sistema comercial simplificado, operado por menus no
terminal. O usuário poderá cadastrar produtos, administrar estoque,
montar um carrinho, aplicar descontos, simular pagamentos, concluir
vendas e emitir recibos.

### Objetivos de aprendizagem

-   [ ] Consolidar sintaxe e recursos da linguagem Java.
-   [ ] Praticar encapsulamento, abstração, composição, herança e
    polimorfismo.
-   [ ] Trabalhar com interfaces, enums, generics e collections.
-   [ ] Entender organização em camadas e separação de
    responsabilidades.
-   [ ] Aprender a modelar regras de negócio sem depender de frameworks.
-   [ ] Identificar problemas reais que justifiquem Design Patterns.
-   [ ] Implementar persistência inicialmente em memória e, depois, em
    arquivos e banco relacional.
-   [ ] Desenvolver testes automatizados e código de fácil manutenção.

### Restrições iniciais

-   Java 21.
-   Aplicação executada pelo terminal.
-   Maven para gerenciamento e execução do projeto.
-   Sem Spring, Hibernate/JPA, Lombok ou outros frameworks inicialmente.
-   Persistência inicial em memória, sem banco de dados.
-   Valores monetários representados com `BigDecimal`.
-   Datas representadas com as APIs de data e hora do Java.
-   Não aplicar padrões de projeto apenas para aumentar a quantidade de
    padrões utilizados.

------------------------------------------------------------------------

## 2. Escopo funcional

### Módulo de produtos

-   [ ] Cadastrar produto.
-   [ ] Listar produtos.
-   [ ] Consultar produto por identificador.
-   [ ] Atualizar dados do produto.
-   [ ] Excluir produto, respeitando as regras do domínio.
-   [ ] Associar produto a uma categoria.
-   [ ] Validar nome, preço e demais dados obrigatórios.

**Decisão inicial de modelagem:** enquanto a categoria for a única
diferença entre os produtos, utilizar uma única classe `Produto` e
representar a categoria com `enum`. Não criar subclasses ou factories
específicas sem que surjam diferenças reais de atributos, comportamento
ou processo de criação.

### Módulo de categorias

-   [ ] Definir as categorias disponíveis.
-   [ ] Utilizar `enum` para representar as categorias iniciais.
-   [ ] Validar a categoria informada no cadastro de produtos.

> A utilização de `enum` é suficiente para o escopo inicial. Um CRUD
> independente de categorias só deverá ser criado se houver necessidade
> de categorias dinâmicas, cadastradas durante a execução.

### Módulo de estoque

-   [ ] Consultar quantidade disponível de um produto.
-   [ ] Registrar entrada de mercadorias.
-   [ ] Registrar saída de mercadorias.
-   [ ] Realizar ajuste de quantidade.
-   [ ] Impedir estoque negativo.
-   [ ] Consultar histórico de movimentações.
-   [ ] Validar disponibilidade antes de concluir uma venda.

### Módulo de carrinho

-   [ ] Criar/iniciar um carrinho.
-   [ ] Adicionar produto ao carrinho.
-   [ ] Remover produto do carrinho.
-   [ ] Alterar quantidade de um item.
-   [ ] Consultar itens e quantidades.
-   [ ] Calcular subtotal.
-   [ ] Validar produtos e quantidades.
-   [ ] Impedir quantidades inválidas e tratar indisponibilidade de
    estoque.

### Módulo de descontos

-   [ ] Aplicar desconto percentual.
-   [ ] Aplicar desconto de valor fixo.
-   [ ] Aplicar desconto por cupom.
-   [ ] Validar condições de aplicação.
-   [ ] Impedir desconto superior ao valor permitido.
-   [ ] Exibir subtotal, desconto e total final.

**Padrão a investigar:** Strategy, pois cada modalidade de desconto
possui uma regra de cálculo própria. A aplicação deve ser justificada
pelo problema, não apenas pelo desejo de utilizar o padrão.

### Módulo de vendas e pagamentos

-   [ ] Iniciar o processo de finalização do carrinho.
-   [ ] Revalidar produtos, quantidades e estoque.
-   [ ] Calcular valores finais.
-   [ ] Selecionar uma forma de pagamento simulada.
-   [ ] Simular aprovação ou recusa do pagamento.
-   [ ] Debitar o estoque após a aprovação e validação da operação.
-   [ ] Registrar a venda.
-   [ ] Registrar os itens vendidos e seus valores no momento da compra.
-   [ ] Impedir que uma venda seja registrada parcialmente.
-   [ ] Definir o estado da venda e suas transições válidas.

**Ponto de atenção:** o histórico da venda deve preservar uma fotografia
dos dados comerciais no momento da compra. Alterar posteriormente o nome
ou preço do produto não pode modificar vendas antigas.

### Módulo de recibos e consultas

-   [ ] Gerar recibo/comprovante de venda.
-   [ ] Exibir identificador e data da venda.
-   [ ] Exibir itens, quantidades e preços unitários.
-   [ ] Exibir subtotal, descontos e total.
-   [ ] Exibir forma e resultado do pagamento simulado.
-   [ ] Consultar vendas anteriores.
-   [ ] Consultar detalhes de uma venda.

> O documento inicial será um recibo didático, não uma Nota Fiscal
> eletrônica fiscalmente válida. A emissão de NF-e real exige requisitos
> fiscais e integrações que estão fora do escopo desta versão.

------------------------------------------------------------------------

## 3. Organização arquitetural sugerida

Começar com uma arquitetura simples em camadas. A estrutura pode evoluir
conforme os requisitos forem implementados; não é necessário criar
antecipadamente todos os diretórios e arquivos.

``` text
src/main/java/
├── Main.java
├── presentation/
│   ├── Menu.java
│   ├── ProdutoMenu.java
│   ├── EstoqueMenu.java
│   ├── CarrinhoMenu.java
│   └── VendaMenu.java
├── application/
│   └── services/
│       ├── ProdutoService.java
│       ├── EstoqueService.java
│       ├── CarrinhoService.java
│       ├── DescontoService.java
│       └── VendaService.java
├── domain/
│   ├── entities/
│   ├── enums/
│   ├── interfaces/
│   └── exceptions/
└── infrastructure/
    ├── repositories/
    └── documents/
```

### Responsabilidades

  -----------------------------------------------------------------------
  Camada                              Responsabilidade
  ----------------------------------- -----------------------------------
  `presentation`                      Menus, leitura de entradas e
                                      exibição de informações no
                                      terminal.

  `application`                       Orquestração dos casos de uso e
                                      coordenação dos serviços.

  `domain`                            Entidades, regras de negócio,
                                      enums, contratos e exceções de
                                      domínio.

  `infrastructure`                    Implementações técnicas,
                                      repositórios, arquivos, JDBC e
                                      geração de documentos.
  -----------------------------------------------------------------------

### Regras de organização

-   [ ] Entidades não devem ler dados diretamente do terminal.
-   [ ] Serviços não devem depender de `Scanner` ou de detalhes da
    interface.
-   [ ] Menus devem coletar dados e chamar os serviços apropriados.
-   [ ] Regras de negócio devem ficar no domínio ou nos serviços
    responsáveis pelo caso de uso.
-   [ ] Evitar classes e interfaces sem responsabilidade clara.
-   [ ] Evitar dependências desnecessárias entre módulos.
-   [ ] Manter a aplicação executável ao final de cada etapa.

------------------------------------------------------------------------

## 4. Roadmap por etapas

## Etapa 0 --- Revisão e fundação do projeto

**Objetivo:** preparar a base do projeto e simplificar a modelagem atual
antes de expandir o sistema.

-   [ ] Revisar a implementação atual de Factory Method.
-   [ ] Registrar no README ou em uma nota técnica o que foi aprendido
    com o padrão.
-   [ ] Manter uma branch ou tag com a implementação anterior, se for
    útil para consulta.
-   [ ] Transformar `Produto` em uma classe concreta, caso categoria
    continue sendo sua única variação.
-   [ ] Manter `ProdutoCategoria` como `enum`.
-   [ ] Remover subclasses que não possuem atributos ou comportamentos
    próprios.
-   [ ] Remover factories e selector que não agregam valor ao cenário
    atual.
-   [ ] Definir identificador único para produto.
-   [ ] Definir validações essenciais da entidade.
-   [ ] Organizar os pacotes iniciais.
-   [ ] Garantir que o projeto compile e execute.

**Conceitos Java:** classes, objetos, construtores, encapsulamento,
`enum`, `UUID`, `BigDecimal`, pacotes e modificadores de acesso.

**Critério de conclusão:** existe uma entidade `Produto` coerente com o
domínio, sem hierarquias artificiais, e o projeto compila.

## Etapa 1 --- CRUD de produtos e repositório em memória

**Objetivo:** implementar o primeiro fluxo completo, do terminal até a
camada de domínio e armazenamento.

-   [ ] Criar contrato `ProdutoRepository`.
-   [ ] Criar implementação `ProdutoRepositoryInMemory`.
-   [ ] Implementar salvar.
-   [ ] Implementar buscar por ID.
-   [ ] Implementar listar todos.
-   [ ] Implementar atualizar.
-   [ ] Implementar remover.
-   [ ] Criar `ProdutoService`.
-   [ ] Implementar validações de cadastro e atualização.
-   [ ] Criar menu de produtos.
-   [ ] Tratar identificadores inexistentes e entradas inválidas.
-   [ ] Testar manualmente todos os fluxos.

**Conceitos Java:** interfaces, `List`, `Map`, `HashMap`, `Optional`,
generics, exceções e separação de responsabilidades.

**Critério de conclusão:** o CRUD funciona pelo terminal e as regras não
dependem da implementação em memória.

## Etapa 2 --- Estoque

**Objetivo:** controlar quantidades e registrar movimentações de
estoque.

-   [ ] Definir como o estoque será associado ao produto.
-   [ ] Modelar movimentação de estoque.
-   [ ] Implementar entrada.
-   [ ] Implementar saída.
-   [ ] Implementar ajuste.
-   [ ] Impedir quantidades negativas.
-   [ ] Consultar saldo por produto.
-   [ ] Listar movimentações.
-   [ ] Criar serviço e menu de estoque.
-   [ ] Criar testes para entradas, saídas, ajustes e situações
    inválidas.

**Conceitos Java:** encapsulamento de estado, composição, collections,
datas, exceções específicas e testes unitários.

**Critério de conclusão:** nenhuma operação consegue deixar o estoque em
estado inválido e as movimentações podem ser consultadas.

## Etapa 3 --- Carrinho de compras

**Objetivo:** representar uma compra em andamento e controlar seus
itens.

-   [ ] Criar entidade `Carrinho`.
-   [ ] Criar entidade `ItemCarrinho`.
-   [ ] Definir como identificar produtos dentro do carrinho.
-   [ ] Adicionar produto.
-   [ ] Remover produto.
-   [ ] Alterar quantidade.
-   [ ] Calcular subtotal por item.
-   [ ] Calcular subtotal geral.
-   [ ] Tratar carrinho vazio.
-   [ ] Validar quantidade e existência do produto.
-   [ ] Criar serviço e menu de carrinho.
-   [ ] Criar testes unitários.

**Conceitos Java:** composição, `List`, `Map`, `BigDecimal`, métodos de
domínio e invariantes.

**Critério de conclusão:** o usuário consegue montar e modificar um
carrinho e consultar seus valores corretamente.

## Etapa 4 --- Estratégias de desconto

**Objetivo:** implementar regras de desconto independentes e
extensíveis.

-   [ ] Definir as regras de negócio de cada desconto.
-   [ ] Criar contrato de estratégia de desconto, se a variação
    justificar.
-   [ ] Implementar desconto percentual.
-   [ ] Implementar desconto fixo.
-   [ ] Implementar desconto por cupom.
-   [ ] Validar limites e condições.
-   [ ] Definir se descontos podem ser combinados.
-   [ ] Exibir o cálculo detalhado no carrinho.
-   [ ] Criar testes para cada estratégia e seus limites.

**Conceitos Java:** interfaces, polimorfismo, composição, `BigDecimal`,
validações e testes.

**Pattern a estudar:** Strategy.

**Critério de conclusão:** novas modalidades de desconto podem ser
implementadas sem concentrar todas as regras em condicionais extensos no
carrinho.

## Etapa 5 --- Venda e pagamento simulado

**Objetivo:** transformar um carrinho em uma venda registrada,
preservando a consistência da operação.

-   [ ] Criar entidade `Venda`.
-   [ ] Criar entidade `ItemVenda`.
-   [ ] Definir status da venda.
-   [ ] Definir formas de pagamento simuladas.
-   [ ] Modelar o resultado do pagamento.
-   [ ] Implementar simulação de aprovação e recusa.
-   [ ] Revalidar o estoque no momento da finalização.
-   [ ] Definir uma ordem segura para validação, pagamento e baixa de
    estoque.
-   [ ] Evitar baixa parcial de estoque.
-   [ ] Registrar os dados históricos dos itens vendidos.
-   [ ] Impedir transições inválidas de status.
-   [ ] Criar serviço de venda.
-   [ ] Criar testes de sucesso, recusa e falhas durante a operação.

**Conceitos Java:** modelagem de domínio, interfaces, polimorfismo,
exceções, consistência de estado e testes.

**Patterns a investigar, se necessários:** Strategy para formas de
pagamento; State para ciclo de vida da venda.

**Critério de conclusão:** uma venda aprovada é registrada e o estoque é
atualizado de forma consistente; uma venda recusada ou inválida não gera
baixa indevida nem registro de venda concluída.

## Etapa 6 --- Recibos e histórico

**Objetivo:** apresentar os dados da venda de maneira clara e permitir
consultas posteriores.

-   [ ] Definir o formato do recibo.
-   [ ] Gerar recibo no terminal.
-   [ ] Salvar recibo em arquivo.
-   [ ] Exibir itens, quantidades, preços e descontos.
-   [ ] Exibir total e forma de pagamento.
-   [ ] Listar vendas.
-   [ ] Consultar uma venda por identificador.
-   [ ] Criar testes para a geração do documento.

**Conceitos Java:** `StringBuilder`, formatação, `java.time`,
`java.nio.file` e operações de I/O.

**Pattern a investigar:** Builder somente se a montagem do documento
ficar suficientemente complexa para justificar a abstração.

**Critério de conclusão:** uma venda pode ser consultada e seu recibo
pode ser exibido e salvo.

## Etapa 7 --- Persistência em arquivos

**Objetivo:** manter os dados entre execuções sem ainda introduzir banco
de dados.

-   [ ] Escolher um formato de armazenamento adequado.
-   [ ] Implementar leitura dos dados na inicialização.
-   [ ] Implementar gravação das alterações.
-   [ ] Persistir produtos.
-   [ ] Persistir estoque e movimentações.
-   [ ] Persistir vendas e itens vendidos.
-   [ ] Tratar arquivo inexistente, vazio ou inválido.
-   [ ] Testar encerramento e reinicialização da aplicação.

**Conceitos Java:** I/O, `Path`, `Files`, tratamento de exceções e
representação de dados.

**Critério de conclusão:** os dados necessários continuam disponíveis
após encerrar e iniciar novamente a aplicação.

## Etapa 8 --- PostgreSQL com JDBC

**Objetivo:** aprender acesso a banco relacional diretamente pela API
JDBC, sem ORM.

-   [ ] Modelar tabelas e relacionamentos.
-   [ ] Criar banco e schema PostgreSQL.
-   [ ] Estudar `Connection`, `PreparedStatement` e `ResultSet`.
-   [ ] Implementar repositório JDBC de produtos.
-   [ ] Implementar repositório JDBC de estoque.
-   [ ] Implementar repositório JDBC de vendas.
-   [ ] Utilizar consultas parametrizadas.
-   [ ] Gerenciar conexões e recursos corretamente.
-   [ ] Implementar transações com `commit` e `rollback`.
-   [ ] Garantir que a conclusão da venda e a baixa do estoque sejam
    atômicas no banco.
-   [ ] Testar falhas e rollback.

**Conceitos Java:** JDBC, SQL, `try-with-resources`, interfaces,
transações e exceções.

**Critério de conclusão:** a aplicação utiliza PostgreSQL sem ORM e
consegue concluir ou reverter operações de venda de maneira consistente.

## Etapa 9 --- Qualidade, testes e refatoração

**Objetivo:** elevar a confiabilidade e a qualidade do projeto.

-   [ ] Adicionar JUnit.
-   [ ] Testar regras das entidades.
-   [ ] Testar serviços.
-   [ ] Testar repositórios.
-   [ ] Criar testes de integração com PostgreSQL.
-   [ ] Testar cenários de erro e limites.
-   [ ] Revisar nomes, responsabilidades e acoplamento.
-   [ ] Remover duplicações.
-   [ ] Revisar tratamento de exceções.
-   [ ] Documentar decisões arquiteturais.
-   [ ] Atualizar README com instalação, execução e funcionalidades.

**Conceitos Java:** JUnit, organização de testes, doubles/mocks quando
úteis, refatoração e princípios SOLID.

**Critério de conclusão:** os principais fluxos possuem testes
automatizados e o projeto pode ser executado por outra pessoa seguindo o
README.

------------------------------------------------------------------------

## 5. Design Patterns: guia de aplicação consciente

Os padrões abaixo são possibilidades a investigar durante o
desenvolvimento. Não são requisitos obrigatórios.

  -----------------------------------------------------------------------
  Padrão                  Possível aplicação      Quando considerar
  ----------------------- ----------------------- -----------------------
  Strategy                Descontos e formas de   Quando existem
                          pagamento               algoritmos
                                                  intercambiáveis com
                                                  regras diferentes.

  Factory Method          Criação de objetos      Quando subclasses ou
                                                  implementações precisam
                                                  decidir como criar
                                                  objetos.

  Builder                 Montagem de recibos ou  Quando a construção
                          objetos complexos       possui muitos
                                                  parâmetros ou etapas
                                                  opcionais.

  Observer                Eventos de venda e      Quando múltiplos
                          notificações            interessados precisam
                                                  reagir a um evento sem
                                                  acoplamento direto.

  State                   Estados da venda        Quando o comportamento
                                                  e as transições
                                                  dependem do estado
                                                  atual.

  Decorator               Composição de           Quando for necessário
                          funcionalidades         adicionar
                                                  responsabilidades de
                                                  forma combinável.

  Repository              Acesso a dados          Para separar regras de
                                                  negócio dos detalhes de
                                                  persistência.
  -----------------------------------------------------------------------

### Registro de decisões sobre padrões

Para cada padrão considerado, registrar:

-   **Problema:** qual dificuldade concreta apareceu?
-   **Alternativas:** como seria resolvido sem o padrão?
-   **Decisão:** por que o padrão foi escolhido ou descartado?
-   **Consequência:** o que melhorou e qual complexidade foi adicionada?

  Padrão           Decisão     Justificativa
  ---------------- ----------- ---------------
  Factory Method   A avaliar   
  Strategy         A avaliar   
  Builder          A avaliar   
  Observer         A avaliar   
  State            A avaliar   
  Decorator        A avaliar   

------------------------------------------------------------------------

## 6. Conceitos Java para acompanhar

Use esta lista como referência de estudo ao longo das etapas.

-   [ ] Sintaxe, tipos primitivos e tipos de referência.
-   [ ] Classes, objetos, construtores e sobrecarga.
-   [ ] Encapsulamento e modificadores de acesso.
-   [ ] Herança, abstração e polimorfismo.
-   [ ] Interfaces e classes abstratas.
-   [ ] Enums.
-   [ ] Records.
-   [ ] Generics.
-   [ ] Collections: `List`, `Set`, `Map`, `Queue`.
-   [ ] `HashMap`, `HashSet`, `ArrayList` e `EnumMap`.
-   [ ] `equals()` e `hashCode()`.
-   [ ] Exceções checked e unchecked.
-   [ ] `Optional`.
-   [ ] Lambdas e functional interfaces.
-   [ ] Streams.
-   [ ] `BigDecimal`.
-   [ ] API `java.time`.
-   [ ] I/O e NIO.
-   [ ] Maven.
-   [ ] JUnit.
-   [ ] JDBC.
-   [ ] Transações e gerenciamento de recursos.

------------------------------------------------------------------------

## 7. Critérios gerais de qualidade

Antes de considerar um módulo concluído, verificar:

-   [ ] O requisito funcional está implementado.
-   [ ] As entradas inválidas são tratadas.
-   [ ] As regras de negócio estão protegidas.
-   [ ] As responsabilidades estão separadas.
-   [ ] Não há dependência desnecessária da interface de terminal.
-   [ ] Os valores monetários usam `BigDecimal`.
-   [ ] Os cenários principais e de erro foram testados.
-   [ ] O código compila e a aplicação continua executável.
-   [ ] A decisão por abstrações e padrões está justificada.
-   [ ] O README ou documentação foi atualizado quando necessário.

------------------------------------------------------------------------

## 8. Fluxo de trabalho recomendado

Para cada funcionalidade:

1.  **Entender:** escrever o requisito com suas próprias palavras.
2.  **Modelar:** identificar entidades, atributos, comportamentos e
    regras.
3.  **Planejar:** decidir quais classes e contratos são realmente
    necessários.
4.  **Implementar:** desenvolver a funcionalidade sem antecipar
    requisitos futuros.
5.  **Testar:** validar o fluxo normal, os limites e as falhas.
6.  **Revisar:** avaliar encapsulamento, acoplamento, coesão e uso de
    recursos Java.
7.  **Refatorar:** simplificar o que estiver complexo ou duplicado.
8.  **Documentar:** registrar decisões importantes e marcar os itens
    concluídos.

### Registro de evolução

  Data   Etapa/módulo   O que foi concluído   Dificuldades e decisões
  ------ -------------- --------------------- -------------------------
                                              
                                              
                                              

------------------------------------------------------------------------

## 9. Definição de pronto do projeto

A MiniLoja poderá ser considerada concluída quando:

-   [ ] Todos os módulos funcionais estiverem implementados.
-   [ ] O fluxo completo de compra funcionar pelo terminal.
-   [ ] O estoque for atualizado corretamente após uma venda aprovada.
-   [ ] Vendas e itens vendidos forem preservados no histórico.
-   [ ] Recibos puderem ser gerados e consultados.
-   [ ] Os dados forem persistidos no PostgreSQL por JDBC.
-   [ ] As operações críticas utilizarem transações adequadamente.
-   [ ] Os principais comportamentos possuírem testes automatizados.
-   [ ] O projeto possuir documentação suficiente para instalação e
    execução.
-   [ ] As decisões arquiteturais e o uso de Design Patterns estiverem
    documentados.

**Princípio central:** construir uma solução simples que resolva
corretamente o problema atual. Adicionar abstrações, interfaces e
padrões somente quando houver uma razão concreta para isso.

O foco não é terminar com a maior quantidade de classes ou Design
Patterns, mas demonstrar domínio de Java e capacidade de tomar boas
decisões de engenharia de software.
