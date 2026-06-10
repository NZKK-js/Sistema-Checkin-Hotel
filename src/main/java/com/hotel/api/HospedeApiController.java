package com.hotel.api;

import com.google.gson.JsonSyntaxException;
import com.hotel.model.Hospede;
import com.hotel.service.HospedeService;
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
 * CRUD RESTful de hóspedes.
 * Todas as rotas são protegidas pelo JwtFilter.
 */
@WebServlet("/api/hospedes/*")
public class HospedeApiController extends HttpServlet {

    private final HospedeService hospedeService = new HospedeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Integer id = obterId(req);
            if (id == null) {
                String busca = req.getParameter("busca");
                if (busca != null && !busca.trim().isEmpty()) {
                    JsonUtil.responderJson(resp, HttpServletResponse.SC_OK,
                            hospedeService.buscarPorNome(busca.trim()));
                } else {
                    JsonUtil.responderJson(resp, HttpServletResponse.SC_OK,
                            hospedeService.listarTodos());
                }
                return;
            }

            Hospede hospede = hospedeService.buscarPorId(id);
            if (hospede == null) {
                responderErro(resp, HttpServletResponse.SC_NOT_FOUND, "Hóspede não encontrado.");
                return;
            }

            JsonUtil.responderJson(resp, HttpServletResponse.SC_OK, hospede);
        } catch (NumberFormatException e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, "O ID informado é inválido.");
        } catch (Exception e) {
            responderErro(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível consultar os hóspedes.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Hospede hospede = lerHospede(req);
            int id = hospedeService.cadastrar(hospede);
            hospede.setId(id);

            Map<String, Object> resposta = new LinkedHashMap<>();
            resposta.put("mensagem", "Hóspede cadastrado com sucesso.");
            resposta.put("hospede", hospede);
            JsonUtil.responderJson(resp, HttpServletResponse.SC_CREATED, resposta);
        } catch (JsonSyntaxException e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, "O JSON enviado é inválido.");
        } catch (SQLIntegrityConstraintViolationException e) {
            responderErro(resp, HttpServletResponse.SC_CONFLICT,
                    "Já existe um hóspede cadastrado com este CPF ou e-mail.");
        } catch (Exception e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Integer id = exigirId(req);
            if (hospedeService.buscarPorId(id) == null) {
                responderErro(resp, HttpServletResponse.SC_NOT_FOUND, "Hóspede não encontrado.");
                return;
            }

            Hospede hospede = lerHospede(req);
            hospede.setId(id);
            hospedeService.atualizar(hospede);

            Map<String, Object> resposta = new LinkedHashMap<>();
            resposta.put("mensagem", "Hóspede atualizado com sucesso.");
            resposta.put("hospede", hospede);
            JsonUtil.responderJson(resp, HttpServletResponse.SC_OK, resposta);
        } catch (NumberFormatException e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, "O ID informado é inválido.");
        } catch (JsonSyntaxException e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, "O JSON enviado é inválido.");
        } catch (SQLIntegrityConstraintViolationException e) {
            responderErro(resp, HttpServletResponse.SC_CONFLICT,
                    "Já existe outro hóspede cadastrado com este CPF ou e-mail.");
        } catch (Exception e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Integer id = exigirId(req);
            if (hospedeService.buscarPorId(id) == null) {
                responderErro(resp, HttpServletResponse.SC_NOT_FOUND, "Hóspede não encontrado.");
                return;
            }

            hospedeService.excluir(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, "O ID informado é inválido.");
        } catch (SQLIntegrityConstraintViolationException e) {
            responderErro(resp, HttpServletResponse.SC_CONFLICT,
                    "Este hóspede não pode ser excluído porque possui uma reserva cadastrada.");
        } catch (Exception e) {
            responderErro(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private Hospede lerHospede(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Hospede hospede = JsonUtil.lerJson(req, Hospede.class);
        return hospede != null ? hospede : new Hospede();
    }

    private Integer exigirId(HttpServletRequest req) {
        Integer id = obterId(req);
        if (id == null) {
            throw new NumberFormatException("ID ausente");
        }
        return id;
    }

    private Integer obterId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }

        String[] partes = pathInfo.split("/");
        if (partes.length != 2 || partes[1].trim().isEmpty()) {
            throw new NumberFormatException("Caminho inválido");
        }
        return Integer.parseInt(partes[1]);
    }

    private void responderErro(HttpServletResponse resp, int status, String mensagem) throws IOException {
        String texto = (mensagem == null || mensagem.trim().isEmpty())
                ? "Não foi possível concluir a operação."
                : mensagem;
        JsonUtil.responderJson(resp, status, Map.of("erro", texto));
    }
}
