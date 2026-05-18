package com.hotel.controller;

import com.hotel.service.CheckInService;
import com.hotel.service.ReservaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/checkins/*")
public class CheckInController extends HttpServlet {

    private final CheckInService service = new CheckInService();
    private final ReservaService reservaService = new ReservaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                req.setAttribute("checkins", service.listarTodos());
                req.getRequestDispatcher("/WEB-INF/views/checkins/lista.jsp").forward(req, resp);

            } else if (pathInfo.equals("/novo")) {
                req.setAttribute("reservas", reservaService.listarConfirmadas());
                req.getRequestDispatcher("/WEB-INF/views/checkins/formulario.jsp").forward(req, resp);

            } else if (pathInfo.equals("/checkout")) {
                service.realizarCheckout(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/checkins?msg=checkout");

            } else if (pathInfo.equals("/cancelar")) {
                service.cancelar(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/checkins?msg=cancelado");
            }
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/checkins/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo.equals("/salvar")) {
                int reservaId = Integer.parseInt(req.getParameter("reservaId"));
                String observacoes = req.getParameter("observacoes");
                service.realizarCheckIn(reservaId, observacoes);
                resp.sendRedirect(req.getContextPath() + "/checkins?msg=checkin");
            }
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            try { req.setAttribute("reservas", reservaService.listarConfirmadas()); } catch (Exception ignored) {}
            req.getRequestDispatcher("/WEB-INF/views/checkins/formulario.jsp").forward(req, resp);
        }
    }
}
