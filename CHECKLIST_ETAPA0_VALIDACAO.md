# CHECKLIST — Validação Etapa 0 (MiniLoja)

**Fonte de referência:** `Planejamento-MiniLoja-Java.md` (seção “4. Roadmap por etapas → Etapa 0”).  
**Data da validação/atualização:** 2026-11-20 (ambiente local).  
**Branch observado:** `main` (git).  
**Observação (alinhado agora):** README da raiz atualizado com decisão técnica (remoção temporária de classes extras/fábrica/seletores e foco em `Produto` concreto + `enum`). **Não será criada nova branch na Etapa 0** (decisão registrada). Implementação anterior do **Factory Method revisada** e removida por não agregar valor ao cenário atual.  
**Estado git (resumo):** conferir com `git status --short`.

---

## 1) Checklist das alterações/estado observado (o que existe agora)

### Estrutura/pacotes (observado)
- [x] `domain.entities.Produto` (`src/main/java/domain/entities/Produto.java`)
- [x] `domain.enums.ProdutoCategoria` (`src/main/java/domain/enums/ProdutoCategoria.java`)
- [x] `domain.exceptions.*` inclui `ProductDomainException` (public abstract), `InvalidProductArgumentException`, `InvalidProductQuantityException`, `ProductValueArgumentException`
- [x] `Main.java` instancia `Produto` concreto
- [x] `pom.xml` Java 21 (Maven)

### Modelo (Etapa 0)
- [x] `Produto` é **classe concreta**, sem subclasses no tree atual
- [x] `ProdutoCategoria` é **enum** (`ELETRONICO`, `FERRAMENTA`, `VESTUARIO`)
- [x] Sem `ProdutoFactory*`, `ProdutoFactorySelector`, subclasses antigas no `src/main/java` atual
- [x] Identificador único: `private final UUID id`, gerado com `UUID.randomUUID()` no construtor
- [x] Identificador exposto: `public UUID getId()`

### Validações implementadas em `Produto` (com comentários de motivação no código)
- [x] Construtor: `nome` null ou em branco (`isBlank`) → `InvalidProductArgumentException` (“Nome do produto não pode ser nulo ou vazio”); nome armazenado com `trim()`.
- [x] Construtor: `preco` null ou `< 0` (`compareTo(BigDecimal.ZERO) < 0`) → `InvalidProductArgumentException` (“Preço do produto não pode ser nulo ou negativo”). `preco == 0` é **aceito** (política atual).
- [x] Construtor: `categoria` null → `Objects.requireNonNull` → mensagem “Categoria do produto não pode ser nula”.
- [x] Construtor: `descricao` null → `Objects.requireNonNull` → mensagem “Descrição do produto não pode ser nula”.
- [x] `adicionarQuantidade(Integer)`: `Objects.requireNonNull` (“Quantidade não pode ser nula”); se `< 0` → `InvalidProductQuantityException("Quantidade inválida")`.
- [x] `removerQuantidade(Integer)`: `Objects.requireNonNull` (“Quantidade não pode ser nula”); se `< 0` → `InvalidProductQuantityException("Quantidade inválida")`; se estoque `<= 0` ou `quantidade > estoque` → `InvalidProductQuantityException("Não é possível remover quantidade maior que a disponível")`.
- [x] `quantidade` inicia em `0`; `toString()` inclui id, nome, preco, categoria, quantidade, descrição.
- [x] Hierarquia: `ProductDomainException` `public abstract`; exceções concretas públicas.

### Documentação/histórico (Etapa 0 pede registro)
- [x] Decisão e histórico resumido registrados no `README.md` da raiz (remoção temporária de classes/fábrica/seletores; Factory Method revisado e removido por não agregar valor). Nota técnica detalhada em arquivo próprio é opcional.
- [ ] `PENDENCIAS.md` pode estar **desatualizado** em relação ao refactor atual (revisar conteúdo; se não existir, ignorar)
- [x] Nova branch na Etapa 0: **não será criada** (decisão registrada). Branch/tag anterior não verificada neste fluxo.

### Build/execução
- [x] `mvn -q -DskipTests compile` executado na raiz do repositório com sucesso (exit 0) nesta atualização
- [ ] Execução do `Main` **não executada** nesta atualização (apenas build)

---

## 2) Checklist do plano (Etapa 0) — marcação por item

- [x] Revisar a implementação anterior do Factory Method (realizada); por não agregar valor no cenário atual, foi removida em favor de `Produto` concreto + `enum` (documentado no `README.md` da raiz)
- [~] Registrar o que foi aprendido (README atualizado com a decisão técnica; nota técnica detalhada opcional ainda pode ser expandida)
- [x] Não criar nova branch na Etapa 0 (decisão registrada; branch/tag anterior não exigida neste fluxo)
- [x] Transformar `Produto` em classe concreta (se categoria for única variação)
- [x] Manter `ProdutoCategoria` como `enum`
- [x] Remover subclasses sem atributos/comportamentos próprios
- [x] Remover factories/selector sem valor (no estado atual)
- [x] Definir identificador único para produto (`UUID` + `getId()`)
- [x] Definir/implementar validações essenciais da entidade (construtor + validações de quantidade com `null`/limites; ver ressalvas abaixo)
  - Ressalvas: política de preço permite `0` (não exige `> 0`); não há validação de formato extra além do domínio atual.
- [x] Organizar pacotes iniciais (`domain.*` presentes)
- [~] Garantir que o projeto compile e execute (**compile OK**; execução do `Main` não revalidada nesta passagem)

**Critério de conclusão da Etapa 0 (plano):** “existe uma entidade `Produto` coerente com o domínio, sem hierarquias artificiais, e o projeto compila.”  
- Sem hierarquias artificiais: **atendido**  
- Coerente: **substancialmente atendido** com validações atuais (ainda há decisões de política: preço `0`, docs/histórico pendentes)  
- Compila: **confirmado** (`mvn -q -DskipTests compile`)

---

## 3) Porcentagem do planejamento realizado (escopo: Etapa 0)

**Método:** 11 itens do checklist da Etapa 0. `1.0` concluído, `0.5` parcial, `0.0` pendente/não verificado.

| Item (resumo) | Status | Peso |
|---|---:|---:|
| Revisar Factory Method (histórico) | 1.0 | 1 |
| Registrar aprendizado/decisão (README) | 0.5 | 1 |
| Nova branch na Etapa 0 / branch-tag anterior | 1.0 | 1 |
| `Produto` concreto | 1.0 | 1 |
| `ProdutoCategoria` enum | 1.0 | 1 |
| Remover subclasses inúteis | 1.0 | 1 |
| Remover factories/selector inúteis | 1.0 | 1 |
| Identificador único definido | 1.0 | 1 |
| Validações essenciais da entidade | 1.0 | 1 |
| Organizar pacotes iniciais | 1.0 | 1 |
| Compila e executa | 0.5 | 1 |

**Total:** 10 / 11 = **~91%** (Etapa 0).

**Nota:** revisão do Factory Method registrada (README) e decisão de não criar nova branch na Etapa 0 aplicada. Restam: nota técnica detalhada opcional do aprendizado, `PENDENCIAS.md` desatualizado (se existir), execução do `Main` não registrada e política de `preco == 0` (documentar se mantida).

---

## 4) Ajustes recomendados (curtos)
1. Decidir política de preço: se deve ser `> 0`, ajustar validação e mensagem; caso contrário, documentar que `0` é permitido.
2. Se existir `PENDENCIAS.md`, alinhar ao estado atual (remover referências ao Factory Method/estrutura antiga). Nota técnica detalhada do aprendizado é opcional.
3. (Opcional) Rodar `java -cp target/classes Main` e registrar no checklist.

---

## 5) Comandos úteis
```bash
cd /home/sales/vhost/MiniLoja
mvn -q -DskipTests compile
java -cp target/classes Main
git status --short
```