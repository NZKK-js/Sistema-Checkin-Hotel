package com.hotel.filter;

import com.hotel.util.JsonUtil;
import com.hotel.util.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Protege somente os endpoints da API.
 * As páginas JSP continuam acessíveis pelo navegador para demonstração visual.
 */
@WebFilter("/api/*")
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath()
                + (req.getPathInfo() != null ? req.getPathInfo() : "");

        if (path.startsWith("/api/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            bloquear(resp);
            return;
        }

        String token = authHeader.substring(7).trim();
        if (!JwtUtil.isTokenValido(token)) {
            bloquear(resp);
            return;
        }

        req.setAttribute("emailAutenticado", JwtUtil.validarToken(token));
        chain.doFilter(request, response);
    }

    private void bloquear(HttpServletResponse resp) throws IOException {
        JsonUtil.responderJson(resp, HttpServletResponse.SC_UNAUTHORIZED,
                Map.of("erro", "Acesso não autorizado"));
    }
}
