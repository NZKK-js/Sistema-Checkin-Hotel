package com.hotel.dao;

import com.hotel.model.Usuario;

import java.sql.SQLException;
import java.util.function.Supplier;

public class UsuarioDAO extends MysqlDAO<Usuario> {

    @Override
    protected Supplier<Usuario> novaInstancia() {
        return Usuario::new;
    }

    public void salvar(Usuario usuario) throws SQLException {
        executarUpdate(
            "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)",
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getSenha()
        );
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        return buscarUnico(
            "SELECT * FROM usuarios WHERE email = ?",
            email
        );
    }
}
