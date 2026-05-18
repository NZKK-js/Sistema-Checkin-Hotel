package com.hotel.controller;

import com.hotel.model.Quarto;
import com.hotel.service.QuartoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/quartos/*")
public class QuartoController extends HttpServlet {

    private final QuartoService service = new QuartoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                req.setAttribute("quartos", service.listarTodos());
                req.getRequestDispatcher("/WEB-INF/views/quartos/lista.jsp").forward(req, resp);

            } else if (pathInfo.equals("/novo")) {
                req.getRequestDispatcher("/WEB-INF/views/quartos/formulario.jsp").forward(req, resp);

            } else if (pathInfo.equals("/editar")) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("quarto", service.buscarPorId(id));
                req.getRequestDispatcher("/WEB-INF/views/quartos/formulario.jsp").forward(req, resp);

            } else if (pathInfo.equals("/excluir")) {
                int id = Integer.parseInt(req.getParameter("id"));
                service.excluir(id);
                resp.sendRedirect(req.getContextPath() + "/quartos?msg=excluido");
            }
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/quartos/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();
        try {
            Quarto q = extrairQuarto(req);
            if (pathInfo.equals("/salvar")) {
                service.cadastrar(q);
                resp.sendRedirect(req.getContextPath() + "/quartos?msg=cadastrado");
            } else if (pathInfo.equals("/atualizar")) {
                q.setId(Integer.parseInt(req.getParameter("id")));
                service.atualizar(q);
                resp.sendRedirect(req.getContextPath() + "/quartos?msg=atualizado");
            }
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/quartos/formulario.jsp").forward(req, resp);
        }
    }

    private Quarto extrairQuarto(HttpServletRequest req) {
        Quarto q = new Quarto();
        q.setNumero(req.getParameter("numero"));
        q.setTipo(req.getParameter("tipo"));
        q.setPrecoDiaria(new BigDecimal(req.getParameter("precoDiaria")));
        q.setDisponivel(req.getParameter("disponivel") != null);
        q.setDescricao(req.getParameter("descricao"));
        return q;
    }
}
