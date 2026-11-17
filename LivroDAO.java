package br.com.bookstore.dao;

import br.com.bookstore.model.Livro;

import java.util.List;
import java.util.Optional;

/**
 * Contrato desacoplado: a camada de negócio depende desta interface (DIP)
 * e nunca de JDBC, SQLite ou qualquer detalhe de infraestrutura.
 */
public interface LivroDAO {

    void salvar(Livro livro);

    Optional<Livro> buscarPorId(Long id);

    List<Livro> listarTodos();
}
