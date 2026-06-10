package com.hotel.controller;

import com.google.gson.JsonSyntaxException;
import com.hotel.service.AuthService;
import com.hotel.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Endpoints públicos de autenticação.
 *
 * POST /api/auth/login
 * POST /api/auth/registrar
 *
 * As rotas antigas /auth/* também foram mantidas para facilitar testes.
 */
@WebServlet({"/api/auth/*", "/auth/*"})
public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        if ("/login".equals(pathInfo)) {
            realizarLogin(req, resp);
        } else if ("/registrar".equals(pathInfo)) {
            registrarUsuario(req, resp);
        } else {
            responderErro(resp, HttpServletResponse.SC_NOT_FOUND, "Endpoint não encontrado.");
        }
    }

    private void realizarLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Credenciais credenciais = lerCredenciais(req);
            String token = authService.login(credenciais.email, credenciais.senha);

            Map<String, Object> resposta = new LinkedHashMap<>();
            resposta.put("mensagem", "Login realizado com sucesso.");
            resposta.put("token", token);
            resposta.put("tipo", "Bearer");
            JsonUtil.responderJson(resp, HttpServletResponse.SC_OK, resposta);
        } catch (JsonSyntaxException e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, "O JSON enviado é inválido.");
        } catch (Exception e) {
            responderErro(resp, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private void registrarUsuario(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            NovoUsuario usuario = lerNovoUsuario(req);
            authService.registrar(usuario.nome, usuario.email, usuario.senha);
            JsonUtil.responderJson(resp, HttpServletResponse.SC_CREATED,
                    Map.of("mensagem", "Usuário registrado com sucesso."));
        } catch (JsonSyntaxException e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, "O JSON enviado é inválido.");
        } catch (SQLIntegrityConstraintViolationException e) {
            responderErro(resp, HttpServletResponse.SC_CONFLICT,
                    "Já existe um usuário cadastrado com este e-mail.");
        } catch (Exception e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private Credenciais lerCredenciais(HttpServletRequest req) throws IOException {
        if (ehJson(req)) {
            Credenciais credenciais = JsonUtil.lerJson(req, Credenciais.class);
            return credenciais != null ? credenciais : new Credenciais();
        }

        Credenciais credenciais = new Credenciais();
        credenciais.email = req.getParameter("email");
        credenciais.senha = req.getParameter("senha");
        return credenciais;
    }

    private NovoUsuario lerNovoUsuario(HttpServletRequest req) throws IOException {
        if (ehJson(req)) {
            NovoUsuario usuario = JsonUtil.lerJson(req, NovoUsuario.class);
            return usuario != null ? usuario : new NovoUsuario();
        }

        NovoUsuario usuario = new NovoUsuario();
        usuario.nome = req.getParameter("nome");
        usuario.email = req.getParameter("email");
        usuario.senha = req.getParameter("senha");
        return usuario;
    }

    private boolean ehJson(HttpServletRequest req) {
        return req.getContentType() != null
                && req.getContentType().toLowerCase().contains("application/json");
    }

    private void responderErro(HttpServletResponse resp, int status, String mensagem) throws IOException {
        String texto = (mensagem == null || mensagem.trim().isEmpty())
                ? "Não foi possível concluir a operação."
                : mensagem;
        JsonUtil.responderJson(resp, status, Map.of("erro", texto));
    }

    private static class Credenciais {
        private String email;
        private String senha;
    }

    private static class NovoUsuario {
        private String nome;
        private String email;
        private String senha;
    }
}
