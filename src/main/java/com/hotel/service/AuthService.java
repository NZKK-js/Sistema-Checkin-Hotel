package com.hotel.service;

import com.hotel.dao.UsuarioDAO;
import com.hotel.model.Usuario;
import com.hotel.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Registra um novo usuário com a senha em hash BCrypt.
     */
    public void registrar(String nome, String email, String senhaPlana) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new Exception("Nome é obrigatório.");
        if (email == null || !email.contains("@"))  throw new Exception("E-mail inválido.");
        if (senhaPlana == null || senhaPlana.length() < 6)
            throw new Exception("A senha deve ter pelo menos 6 caracteres.");

        String hash = BCrypt.hashpw(senhaPlana, BCrypt.gensalt());
        usuarioDAO.salvar(new Usuario(nome.trim(), email.trim().toLowerCase(), hash));
    }

    /**
     * Valida e-mail e senha; retorna um token JWT se as credenciais estiverem corretas.
     * Lança exceção com mensagem amigável em caso de falha.
     */
    public String login(String email, String senhaPlana) throws Exception {
        if (email == null || senhaPlana == null) {
            throw new Exception("E-mail e senha são obrigatórios.");
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(email.trim().toLowerCase());

        if (usuario == null || !BCrypt.checkpw(senhaPlana, usuario.getSenha())) {
            throw new Exception("E-mail ou senha inválidos.");
        }

        return JwtUtil.gerarToken(usuario.getEmail());
    }
}
