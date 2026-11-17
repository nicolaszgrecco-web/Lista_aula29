package br.com.bookstore;

import br.com.bookstore.dao.LivroDAO;
import br.com.bookstore.dao.LivroDAOSqlite;
import br.com.bookstore.model.Livro;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // DIP: o código cliente só enxerga o contrato.
        LivroDAO dao = new LivroDAOSqlite();

        // 1) Persistir 3 obras e conferir os IDs gerados
        System.out.println("=== 1) Salvando livros ===");
        Livro[] novos = {
                new Livro("Clean Code", "Robert C. Martin", 89.90),
                new Livro("Design Patterns", "Erich Gamma et al.", 129.50),
                new Livro("Refactoring", "Martin Fowler", 109.00)
        };
        for (Livro l : novos) {
            dao.salvar(l);
            System.out.println("Salvo com id " + l.getId() + ": " + l);
        }

        // 2) Buscar o ID 2 com API funcional
        System.out.println("\n=== 2) Buscando o livro de ID 2 ===");
        dao.buscarPorId(2L).ifPresentOrElse(
                l -> System.out.println("Encontrado: " + l),
                () -> System.out.println("Aviso: nenhum livro com o ID 2."));

        // 3) ID inexistente: sem NullPointerException
        System.out.println("\n=== 3) Buscando ID inexistente (999) ===");
        dao.buscarPorId(999L).ifPresentOrElse(
                l -> System.out.println("Encontrado: " + l),
                () -> System.out.println("Aviso: nenhum livro com o ID 999. Nenhuma exceção foi lançada."));

        // 4) Listagem tabular
        System.out.println("\n=== 4) Catálogo completo ===");
        List<Livro> todos = dao.listarTodos();
        System.out.printf("%-4s | %-20s | %-22s | %10s%n", "ID", "TÍTULO", "AUTOR", "PREÇO");
        System.out.println("-".repeat(66));
        for (Livro l : todos) {
            System.out.printf("%-4d | %-20s | %-22s | R$ %7.2f%n",
                    l.getId(), l.getTitulo(), l.getAutor(), l.getPreco());
        }
    }
}
