package br.com.bookstore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fábrica central de conexões. Trocar de banco = mudar apenas a URL aqui.
 */
public final class ConexaoFactory {

    private static final String URL = "jdbc:sqlite:bookstore.db";

    private ConexaoFactory() {
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
