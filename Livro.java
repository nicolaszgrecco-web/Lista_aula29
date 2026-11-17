package br.com.bookstore.model;

/**
 * Entidade de domínio: não conhece JDBC nem banco de dados (SRP).
 */
public class Livro {

    private Long id;
    private final String titulo;
    private final String autor;
    private final double preco;

    /** Construtor completo: usado para hidratar tuplas vindas do banco. */
    public Livro(Long id, String titulo, String autor, double preco) {
        this.id = id;
        this.titulo = validarTexto(titulo, "título");
        this.autor = validarTexto(autor, "autor");
        if (preco <= 0.0) {
            throw new IllegalArgumentException("O preço de venda deve ser estritamente positivo");
        }
        this.preco = preco;
    }

    /** Construtor de negócio: livro novo, ainda sem id (será gerado pelo banco). */
    public Livro(String titulo, String autor, double preco) {
        this(null, titulo, autor, preco);
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().length() < 2) {
            throw new IllegalArgumentException(
                    "O " + campo + " não pode ser nulo, vazio ou ter menos de 2 caracteres");
        }
        return valor.trim();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPreco() {
        return preco;
    }

    /** Único setter permitido: sincroniza a PK gerada pelo banco. */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Livro{id=%s, titulo='%s', autor='%s', preco=R$ %.2f}",
                id, titulo, autor, preco);
    }
}
