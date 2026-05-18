package com.hotel.controller;

import com.hotel.service.HospedeService;
import com.hotel.service.QuartoService;
import com.hotel.service.ReservaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/home", "/inicio"})
public class HomeController extends HttpServlet {

    private final HospedeService hospedeService = new HospedeService();
    private final QuartoService quartoService = new QuartoService();
    private final ReservaService reservaService = new ReservaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("totalHospedes", hospedeService.listarTodos().size());
            req.setAttribute("totalQuartos", quartoService.listarTodos().size());
            req.setAttribute("totalReservas", reservaService.listarTodas().size());
        } catch (Exception e) {
            req.setAttribute("totalHospedes", 0);
            req.setAttribute("totalQuartos", 0);
            req.setAttribute("totalReservas", 0);
        }
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
