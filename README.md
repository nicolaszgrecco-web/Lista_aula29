# 📚 BookStore Enterprise — Padrão DAO com Java + SQLite

Projeto da **Aula 29** (Padrão DAO, Inversão de Dependência e Mapeamento Objeto-Relacional).
Catálogo de livros com separação de responsabilidades: modelo de domínio, contrato DAO e implementação SQLite.

## Estrutura

```
src/main/java/br/com/bookstore/
├── Main.java                  # Roteiro de testes (usa só a interface LivroDAO)
├── model/
│   └── Livro.java             # Entidade + invariantes de domínio
└── dao/
    ├── LivroDAO.java          # Contrato (interface)
    ├── LivroDAOSqlite.java    # Implementação JDBC/SQLite
    └── ConexaoFactory.java    # Fábrica de conexões
```

## Como executar

Requisitos: **JDK 17+** e **Maven 3.8+**.

```bash
mvn compile exec:java
```

O arquivo `bookstore.db` é criado automaticamente na pasta de execução. Para recomeçar do zero (IDs 1, 2, 3), apague-o antes de rodar de novo.

## Decisões de projeto

- **`Livro`**: dois construtores (completo, para hidratar do banco; de negócio, com `id = null`). Lança `IllegalArgumentException` se o título/autor for nulo ou tiver menos de 2 caracteres após `trim()`, ou se o preço for ≤ 0. O único setter é `setId`, que sincroniza a PK gerada.
- **`LivroDAO`**: contrato com `salvar`, `buscarPorId` (retorna `Optional<Livro>`) e `listarTodos`. A camada de negócio nunca conhece JDBC.
- **`LivroDAOSqlite`**: cria a tabela no construtor (`CREATE TABLE IF NOT EXISTS`), usa `PreparedStatement` com parâmetros `?` (sem risco de SQL Injection), `try-with-resources` em `Connection`, `PreparedStatement` e `ResultSet`, e `Statement.RETURN_GENERATED_KEYS` para recuperar o ID autoincrementado. A extração das colunas fica isolada em `mapearLinha(ResultSet)`.
- **`Main`**: `LivroDAO dao = new LivroDAOSqlite();` (DIP). Salva 3 obras, busca o ID 2 com `ifPresentOrElse`, busca o ID 999 sem `NullPointerException` e lista o catálogo em formato tabular.

## Respostas das questões teóricas

| Questão | Resposta | Justificativa |
|---|---|---|
| 1 — Impedance Mismatch | **B** | Grafos de objetos ricos na RAM (comportamento, encapsulamento, identidade) não casam com tabelas relacionais de dados atômicos (tuplas e chaves primárias). |
| 2 — Violação do SRP | **C** | `Produto` acopla regra de negócio a detalhes de infraestrutura (JDBC), ganha duas razões para mudar e não pode ser testada sem banco ativo. |
| 3 — DIP com interface DAO | **A** | O controller depende do contrato, então dá para trocar SQLite, PostgreSQL ou um Mock sem alterar a regra de negócio. |
| 4 — `Optional` | **D** | Deixa explícito na assinatura que o resultado pode estar ausente e obriga o chamador a tratar isso, evitando `NullPointerException` acidental. |

## Saída esperada (resumo)

```
=== 1) Salvando livros ===
Salvo com id 1: Livro{id=1, titulo='Clean Code', ...}
Salvo com id 2: Livro{id=2, titulo='Design Patterns', ...}
Salvo com id 3: Livro{id=3, titulo='Refactoring', ...}
...
```
