package com.hotel.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    // Chave secreta — em produção carregue de variável de ambiente
    private static final String SECRET = "HotelCheckinSistemaSeguroChaveSecreta2025!";
    private static final long EXPIRACAO_MS = 1000L * 60 * 60 * 8; // 8 horas

    private static final SecretKey CHAVE = Keys.hmacShaKeyFor(
        SECRET.getBytes(StandardCharsets.UTF_8)
    );

    /** Gera um token JWT para o e-mail informado. */
    public static String gerarToken(String email) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRACAO_MS))
            .signWith(CHAVE)
            .compact();
    }

    /** Valida o token e retorna o e-mail (subject). Lança exceção se inválido. */
    public static String validarToken(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(CHAVE)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
    }

    /** Retorna true se o token for válido, false caso contrário. */
    public static boolean isTokenValido(String token) {
        try {
            validarToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
