package com.hotel.controller;

import com.hotel.model.Hospede;
import com.hotel.service.HospedeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/hospedes/*")
public class HospedeController extends HttpServlet {

    private final HospedeService service = new HospedeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Listar todos
                String busca = req.getParameter("busca");
                if (busca != null && !busca.trim().isEmpty()) {
                    req.setAttribute("hospedes", service.buscarPorNome(busca));
                    req.setAttribute("busca", busca);
                } else {
                    req.setAttribute("hospedes", service.listarTodos());
                }
                req.getRequestDispatcher("/WEB-INF/views/hospedes/lista.jsp").forward(req, resp);

            } else if (pathInfo.equals("/novo")) {
                req.getRequestDispatcher("/WEB-INF/views/hospedes/formulario.jsp").forward(req, resp);

            } else if (pathInfo.equals("/editar")) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("hospede", service.buscarPorId(id));
                req.getRequestDispatcher("/WEB-INF/views/hospedes/formulario.jsp").forward(req, resp);

            } else if (pathInfo.equals("/excluir")) {
                int id = Integer.parseInt(req.getParameter("id"));
                service.excluir(id);
                resp.sendRedirect(req.getContextPath() + "/hospedes?msg=Hóspede removido com sucesso!");
            }
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/hospedes/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            Hospede hospede = extrairHospede(req);

            if (pathInfo.equals("/salvar")) {
                service.cadastrar(hospede);
                resp.sendRedirect(req.getContextPath() + "/hospedes?msg=cadastrado");
            } else if (pathInfo.equals("/atualizar")) {
                hospede.setId(Integer.parseInt(req.getParameter("id")));
                service.atualizar(hospede);
                resp.sendRedirect(req.getContextPath() + "/hospedes?msg=atualizado");
            }

        } catch (Exception e) {

            String mensagem = e.getMessage();

            if (mensagem != null && mensagem.toLowerCase().contains("duplicate")) {
                mensagem = "Já existe um hóspede cadastrado com este CPF.";
            }

            req.setAttribute("erro", mensagem);
            req.getRequestDispatcher("/WEB-INF/views/hospedes/formulario.jsp").forward(req, resp);
        }
    }

    private Hospede extrairHospede(HttpServletRequest req) {
        Hospede h = new Hospede();
        h.setNome(req.getParameter("nome"));
        h.setCpf(req.getParameter("cpf"));
        h.setEmail(req.getParameter("email"));
        h.setTelefone(req.getParameter("telefone"));
        String dataNasc = req.getParameter("dataNascimento");
        if (dataNasc != null && !dataNasc.isEmpty()) {
            h.setDataNascimento(LocalDate.parse(dataNasc));
        }
        return h;
    }
}
