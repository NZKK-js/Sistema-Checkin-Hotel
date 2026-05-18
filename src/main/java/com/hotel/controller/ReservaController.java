package com.hotel.controller;

import com.hotel.model.Reserva;
import com.hotel.service.HospedeService;
import com.hotel.service.QuartoService;
import com.hotel.service.ReservaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/reservas/*")
public class ReservaController extends HttpServlet {

    private final ReservaService service = new ReservaService();
    private final HospedeService hospedeService = new HospedeService();
    private final QuartoService quartoService = new QuartoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                req.setAttribute("reservas", service.listarTodas());
                req.getRequestDispatcher("/WEB-INF/views/reservas/lista.jsp").forward(req, resp);

            } else if (pathInfo.equals("/nova")) {
                req.setAttribute("hospedes", hospedeService.listarTodos());
                req.setAttribute("quartos", quartoService.listarDisponiveis());
                req.getRequestDispatcher("/WEB-INF/views/reservas/formulario.jsp").forward(req, resp);

            } else if (pathInfo.equals("/editar")) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("reserva", service.buscarPorId(id));
                req.setAttribute("hospedes", hospedeService.listarTodos());
                req.setAttribute("quartos", quartoService.listarTodos());
                req.getRequestDispatcher("/WEB-INF/views/reservas/formulario.jsp").forward(req, resp);

            } else if (pathInfo.equals("/confirmar")) {
                service.confirmar(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/reservas?msg=confirmada");

            } else if (pathInfo.equals("/cancelar")) {
                service.cancelar(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/reservas?msg=cancelada");

            } else if (pathInfo.equals("/excluir")) {
                service.excluir(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/reservas?msg=excluida");
            }
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/reservas/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();
        try {
            Reserva reserva = extrairReserva(req);
            if (pathInfo.equals("/salvar")) {
                service.criar(reserva);
                resp.sendRedirect(req.getContextPath() + "/reservas?msg=criada");
            } else if (pathInfo.equals("/atualizar")) {
                reserva.setId(Integer.parseInt(req.getParameter("id")));
                service.atualizar(reserva);
                resp.sendRedirect(req.getContextPath() + "/reservas?msg=atualizada");
            }
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            try {
                req.setAttribute("hospedes", hospedeService.listarTodos());
                req.setAttribute("quartos", quartoService.listarTodos());
            } catch (Exception ignored) {}
            req.getRequestDispatcher("/WEB-INF/views/reservas/formulario.jsp").forward(req, resp);
        }
    }

    private Reserva extrairReserva(HttpServletRequest req) {
        Reserva r = new Reserva();
        r.setHospedeId(Integer.parseInt(req.getParameter("hospedeId")));
        r.setQuartoId(Integer.parseInt(req.getParameter("quartoId")));
        r.setDataEntrada(LocalDate.parse(req.getParameter("dataEntrada")));
        r.setDataSaida(LocalDate.parse(req.getParameter("dataSaida")));
        return r;
    }
}
