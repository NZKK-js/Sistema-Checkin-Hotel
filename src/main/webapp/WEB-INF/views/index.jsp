<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Hotel Check-in System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><span>🏨</span> HotelSystem</a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/" class="active">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/hospedes">Hóspedes</a></li>
        <li><a href="${pageContext.request.contextPath}/quartos">Quartos</a></li>
        <li><a href="${pageContext.request.contextPath}/reservas">Reservas</a></li>
        <li><a href="${pageContext.request.contextPath}/checkins">Check-ins</a></li>
    </ul>
</nav>

<div class="container">
    <div class="card" style="margin-bottom:30px;">
        <div class="card-body" style="text-align:center; padding: 40px;">
            <h1 style="font-size:2rem; color:#1a237e; margin-bottom:10px;">🏨 Sistema de Check-in de Hotel</h1>
            <p style="color:#666; font-size:1rem;">Controle de hóspedes, quartos, reservas e check-ins de forma simples, organizada e prática.</p>
        </div>
    </div>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="number"></div>
            <div class="label">Hóspedes cadastrados</div>
            <a href="${pageContext.request.contextPath}/hospedes" class="btn btn-primary" style="margin-top:12px;">Gerenciar</a>
        </div>
        <div class="stat-card">
            <div class="number">${totalQuartos}</div>
            <div class="label">Quartos disponíveis</div>
            <a href="${pageContext.request.contextPath}/quartos" class="btn btn-primary" style="margin-top:12px;">Gerenciar</a>
        </div>
        <div class="stat-card">
            <div class="number">${totalReservas}</div>
            <div class="label">Reservas ativas</div>
            <a href="${pageContext.request.contextPath}/reservas" class="btn btn-primary" style="margin-top:12px;">Gerenciar</a>
        </div>
        <div class="stat-card">
            <div class="number">✅</div>
            <div class="label">Check-ins</div>
            <a href="${pageContext.request.contextPath}/checkins" class="btn btn-primary" style="margin-top:12px;">Gerenciar</a>
        </div>
    </div>
</div>

<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
