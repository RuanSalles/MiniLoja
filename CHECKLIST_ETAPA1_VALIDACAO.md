# CHECKLIST — Validação Etapa 1 (MiniLoja)

**Fonte de referência:** `Planejamento-MiniLoja-Java.md` → “4. Roadmap por etapas → Etapa 1 — CRUD de produtos e repositório em memória”.  
**Data da validação/atualização:** 2026-11-20 (ambiente local).  
**Branch observado:** `main` (git).  
**Commit de origem da implementação:** `894f24f iniciada implementação`.  
**Critério de conclusão do plano:** “o CRUD funciona pelo terminal e as regras não dependem da implementação em memória”.  
**Resultado da aderência:** **PARCIAL (≈71%)** — contrato e repositório em memória prontos e cadastro funcionando; atualização, remoção, buscas e tratamento de entradas ainda não chegaram ao terminal.  
**Checklist da etapa anterior:** `CHECKLIST_ETAPA0_VALIDACAO.md`.

---

## 1) Estado observado — o que foi montado até agora

### Estrutura/camadas (observado)
- [x] `domain.entities.Produto` (`src/main/java/domain/entities/Produto.java`)
- [x] `domain.enums.ProdutoCategoria` (`src/main/java/domain/enums/ProdutoCategoria.java`)
- [x] `domain.exceptions.*` (`ProductDomainException`, `InvalidProductArgumentException`, `InvalidProductQuantityException`, `ProductValueArgumentException`)
- [x] `infra.repositories.ProdutoRepository` — contrato (`ProdutoRepository.java:9`)
- [x] `infra.repositories.ProdutoRepositoryInMemory` — implementação com `Map<UUID, Produto>` (`ProdutoRepositoryInMemory.java:7-8`)
- [x] `application.ProdutoService` — serviço de aplicação (`ProdutoService.java:9`)
- [x] `presentation.Menu` — menu principal (`Menu.java:5`)
- [x] `presentation.ProdutoMenu` — menu de produtos (`ProdutoMenu.java:10`)
- [x] `Main` — composição das dependências na raiz (`Main.java:14-21`)
- [x] `pom.xml` Java 21 (Maven), sem dependências de frameworks

### Contrato e repositório (Etapa 1)
- [x] Contrato `ProdutoRepository` com `salvar`, `buscarPorId`, `buscarTodos`, `atualizar`, `excluir` (`ProdutoRepository.java:10-14`)
- [x] `salvar` implementado com `Map.put(...)` (`ProdutoRepositoryInMemory.java:11-13`)
- [x] `buscarPorId` implementado com `Optional.ofNullable(...)` (`ProdutoRepositoryInMemory.java:16-18`)
- [x] `listar todos` implementado como `buscarTodos()` devolvendo cópia (`new ArrayList<>(...)`) (`ProdutoRepositoryInMemory.java:21-23`)
- [x] `atualizar` implementado no repositório, com verificação de existência (`ProdutoRepositoryInMemory.java:26-31`)
- [x] `remover` implementado no repositório como `excluir(...)`, com verificação de existência (`ProdutoRepositoryInMemory.java:34-39`)
- [x] Identificador `UUID` gerado na entidade e usado como chave do mapa (`Produto.java:32`, `Produto.java:58`)

### Serviço e terminal (Etapa 1)
- [x] `ProdutoService` criado (`ProdutoService.java:9`)
- [x] `cadastrarProduto(...)` implementado no serviço (`ProdutoService.java:16-21`)
- [x] Menu principal com acesso ao módulo de produtos (`Menu.java:18-25`)
- [x] Menu de produtos com as opções 1 a 5 exibidas (`ProdutoMenu.java:24-29`)
- [x] Opção `1 - Cadastrar Produto` funcional (`ProdutoMenu.java:36`, `ProdutoMenu.java:42-65`)
- [x] Cadastro pelo terminal verificado manualmente (produto “Teclado”, ID UUID, preço `150.50`, categoria `ELETRONICO`)
- [~] Opções `2` a `5` do menu **apenas exibidas**: `switch` trata somente o `case 1` (`ProdutoMenu.java:35-37`)
- [~] Serviço expõe somente cadastro; não há `buscarPorId`, `listarTodos`, `atualizar` ou `excluir` em `ProdutoService`
- [ ] Menu principal não oferece a opção `0 - Sair` (o loop apenas a aceita) (`Menu.java:17-28`)
- [ ] Nenhum teste automatizado (`src/test` inexistente) — previsto originalmente para a Etapa 9

### Validações e tratamento de erros
- [x] Validações de cadastro presentes na entidade: nome nulo/em branco, preço nulo/negativo, categoria e descrição nulas (`Produto.java:19-36`)
- [x] Estoque não negativo protegido na entidade (`Produto.java:62-84`) — heredado da Etapa 0
- [~] Identificador inexistente tratado no repositório com `IllegalArgumentException("Produto não encontrado")` (`ProdutoRepositoryInMemory.java:27-29`, `:35-37`)
- [ ] Exceção de domínio específica para “produto não encontrado” (a regra hoje usa exceção genérica da JVM)
- [ ] Entradas inválidas pelo terminal não tratadas: `InputMismatchException` em opções de menu e `NumberFormatException` em preço encerram a aplicação com stack trace (verificado em execução)
- [ ] `UUID` inválido informado em buscar/atualizar/excluir não tratado
- [ ] Categoria inválida (`IllegalArgumentException` de `Enum.valueOf`) não tratada no menu

### Build/execução
- [x] `mvn -q -DskipTests compile` executado na raiz com sucesso (exit 0)
- [x] `java -cp target/classes Main` executado com sucesso; fluxo de cadastro concluído (exit 0)
- [x] Falhas com entradas inválidas reproduzidas (opção não numérica e preço não numérico) para subsidiar as melhorias

---

## 2) Checklist do plano (Etapa 1) — marcação por item

| # | Item do planejamento | Status | Evidência / aderência |
|---|---|---:|---|
| 1 | Criar contrato `ProdutoRepository` | [x] | `ProdutoRepository.java:9-15`. Aderente: contrato no pacote de infraestrutura, consumido pela camada de aplicação. |
| 2 | Criar implementação `ProdutoRepositoryInMemory` | [x] | `ProdutoRepositoryInMemory.java:7-39`, `Map<UUID, Produto>`. Aderente ao escopo “persistência inicial em memória”. |
| 3 | Implementar salvar | [x] | `ProdutoRepositoryInMemory.java:11-13`. Aderente; usa o `UUID` da entidade como chave. |
| 4 | Implementar buscar por ID | [x] | `ProdutoRepositoryInMemory.java:16-18`, retorno `Optional<Produto>`. Aderente ao conceito `Optional` previsto no plano. |
| 5 | Implementar listar todos | [x] | `ProdutoRepositoryInMemory.java:21-23`. Aderente; devolve cópia da coleção. Observação: `HashMap` não garante ordem. |
| 6 | Implementar atualizar | [~] | Implementado no repositório (`:26-31`), mas sem exposição em `ProdutoService`/`ProdutoMenu`. Critério “CRUD pelo terminal” não atendido para este item. |
| 7 | Implementar remover | [~] | Implementado como `excluir` no repositório (`:34-39`), mas sem exposição em `ProdutoService`/`ProdutoMenu`. |
| 8 | Criar `ProdutoService` | [~] | Criado (`ProdutoService.java:9-22`), porém depende de `ProdutoRepositoryInMemory` (classe concreta) em vez do contrato `ProdutoRepository` — fere “as regras não dependem da implementação em memória”. |
| 9 | Implementar validações de cadastro e atualização | [~] | Cadastro coberto pelas guard clauses de `Produto` (`:19-36`); não há fluxo de atualização nem validação de existência no serviço. |
| 10 | Criar menu de produtos | [~] | Menu criado e cadastro funcional (`ProdutoMenu.java:19-65`); opções 2 a 5 sem implementação no `switch` (`:35-37`). |
| 11 | Tratar identificadores inexistentes e entradas inválidas | [~] | ID inexistente gera `IllegalArgumentException` no repositório; entradas de texto/opção e categoria inválidas ainda não tratadas. |
| 12 | Testar manualmente todos os fluxos | [~] | Build OK e cadastro testado manualmente; listar, buscar, atualizar, excluir e cenários de erro não testados (não estão disponíveis no terminal). |

**Critério de conclusão da Etapa 1:**
- CRUD funciona pelo terminal: **NÃO** — apenas `cadastrar` está operacional.
- Regras não dependem da implementação em memória: **PARCIAL** — existe contrato, mas `ProdutoService` recebe `ProdutoRepositoryInMemory`.
- Projeto compila e executa: **SIM** — `mvn compile` e cadastro manual OK.

---

## 3) Aderência às regras de organização do planejamento

| Regra (seção 3 do planejamento) | Aderência | Observação |
|---|---|---|
| Entidades não leem dados do terminal | [x] | `Produto` não usa `Scanner`. |
| Serviços não dependem de `Scanner`/interface | [x] | `ProdutoService` não recebe `Scanner`. |
| Menus coletam dados e chamam serviços | [~] | Cadastro OK; demais operações não chamam serviço porque ele não as expõe. |
| Regras de negócio no domínio ou serviços | [~] | Validações de atributos no domínio; “produto não encontrado” está no repositório como `IllegalArgumentException`. |
| Evitar classes/interfaces sem responsabilidade clara | [x] | Contrato, implementação, serviço e menus têm papéis distintos. |
| Evitar dependências desnecessárias | [~] | `ProdutoService` e `Main` referenciam a implementação concreta; `Main` possui imports não utilizados (`Produto`, `ProdutoCategoria`, `BigDecimal`). |
| Manter aplicação executável ao final da etapa | [x] | Compila e executa; fluxo de cadastro conclui com sucesso. |

**Aderência arquitetural geral:** **parcial, em evolução.** A separação em camadas foi iniciada corretamente, mas a inversão de dependência (service → interface) e a completeness do CRUD pelo terminal ainda precisam ser concluídas para atender ao critério da Etapa 1.

**Observação de nomenclatura:** o plano sugere `infrastructure/repositories`; o código usa `infra/repositories`. Não é bloqueante, mas deve ser decidido e uniformizado (ver melhoria 6).

---

## 4) Porcentagem do planejamento realizado (escopo: Etapa 1)

**Método:** 12 itens do checklist da Etapa 1. `1.0` concluído, `0.5` parcial, `0.0` pendente/não verificado.

| Item (resumo) | Status | Peso |
|---|---:|---:|
| Contrato `ProdutoRepository` | 1.0 | 1 |
| Implementação em memória | 1.0 | 1 |
| Salvar | 1.0 | 1 |
| Buscar por ID | 1.0 | 1 |
| Listar todos | 1.0 | 1 |
| Atualizar (somente repositório) | 0.5 | 1 |
| Remover (somente repositório) | 0.5 | 1 |
| `ProdutoService` (somente cadastro) | 0.5 | 1 |
| Validações de cadastro/atualização | 0.5 | 1 |
| Menu de produtos (somente cadastro) | 0.5 | 1 |
| IDs inexistentes/entradas inválidas | 0.5 | 1 |
| Testes manuais de todos os fluxos | 0.5 | 1 |

**Total:** 8,5 / 12 = **~71%** (Etapa 1).

**Leitura:** a camada de dados está praticamente completa; o gargalo está na camada de aplicação e apresentação, que ainda não entregam o CRUD ao usuário do terminal.

---

## 5) Melhorias recomendadas (priorizadas)

### Prioridade 1 — necessárias para concluir a Etapa 1
1. **Inverter a dependência do serviço:** trocar `ProdutoRepositoryInMemory` por `ProdutoRepository` em `ProdutoService` (construtor e campo `private final`). Isso atende diretamente ao critério “as regras não dependem da implementação em memória”.
2. **Completar `ProdutoService`:** adicionar `buscarPorId(UUID)`, `listarTodos()`, `atualizar(...)` e `excluir(UUID)`; centralizar a verificação de existência no serviço e lançar uma exceção de domínio específica (ex.: `ProdutoNaoEncontradoException extends ProductDomainException`) em vez de `IllegalArgumentException` no repositório.
3. **Completar `ProdutoMenu`:** implementar os `cases 2` a `5` e exibir o `UUID` na listagem para que o usuário possa informar o identificador nas operações de buscar/atualizar/excluir.
4. **Tratar entradas inválidas no terminal:** capturar `InputMismatchException`, `NumberFormatException` e `IllegalArgumentException` (inclusive `UUID.fromString` e `Enum.valueOf`), exibindo mensagens claras e limpando o buffer do `Scanner`, sem encerrar a aplicação com stack trace.
5. **Adicionar `0 - Sair` ao menu principal** (`Menu.java`) para encerramento explícito e consistente com o menu de produtos.

### Prioridade 2 — robustez e consistência
6. **Uniformizar pacotes:** decidir entre `infra.repositories` (atual) e `infrastructure.repositories` (sugestão do planejamento) e alinhar código, README e planejamento.
7. **Permitir atualização preservando identidade:** hoje `Produto` não tem setters e o `id` é `final`; criar um método de domínio (ex.: `atualizarDados(nome, preco, categoria, descricao)`) reaproveitando as validações do construtor, evitando a troca do `UUID` na atualização.
8. **Tornar a listagem determinística:** ordenar `buscarTodos()` (por nome ou ID) ou trocar `HashMap` por estrutura ordenada, para saída estável no terminal e nos testes.
9. **Revisar política de valores:** decidir se `preço == 0` é válido (pendência já registrada na Etapa 0) e validar descrição em branco, não apenas `null`.
10. **Registrar testes de aceite da Etapa 1:** mesmo com JUnit previsto para a Etapa 9, manter um roteiro curto de comandos/entradas para os 5 fluxos do CRUD no README ou em arquivo de testes manuais.

---

## 6) Comandos úteis / evidências

```bash
cd /home/sales/vhost/MiniLoja

mvn -q -DskipTests compile          # exit 0
java -cp target/classes Main        # cadastro manual OK (exit 0)
git status --short
```

**Roteiro manual já executado (cadastro):**
```
1            # Produtos (menu principal)
1            # Cadastrar Produto
Teclado
150.50
Descricao teste
ELETRONICO
0            # Voltar
0            # Sair
```

**Falhas reproduzidas (entradas inválidas):**
- opção `abc` no menu principal → `java.util.InputMismatchException` (`Menu.java:20`);
- preço `ABC` no cadastro → `java.lang.NumberFormatException` (`ProdutoMenu.java:47`).
