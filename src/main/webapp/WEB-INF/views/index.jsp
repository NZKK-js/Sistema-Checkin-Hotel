<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>HOSPEDAR - Quick Check-in</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
        <img src="${pageContext.request.contextPath}/img/logo-nav.png" alt="HOSPEDAR">
        <span class="brand-text"><b>HOSPEDAR</b> Quick Check-in</span>
    </a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/" class="active">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/hospedes">Hóspedes</a></li>
        <li><a href="${pageContext.request.contextPath}/quartos">Quartos</a></li>
        <li><a href="${pageContext.request.contextPath}/reservas">Reservas</a></li>
        <li><a href="${pageContext.request.contextPath}/checkins">Check-ins</a></li>
    </ul>
</nav>

<div class="container">
    <div class="hero">
        <img class="hero-logo" src="${pageContext.request.contextPath}/img/logo.png" alt="HOSPEDAR - Quick Check-in">
        <h1>Sistema de Check-in de Hotel</h1>
        <p>Gestão de hóspedes, quartos, reservas e check-ins.</p>
    </div>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="icone-topo">
                <svg class="icone icone-grande" viewBox="0 0 24 24" fill="none" stroke="#245580" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M16 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="10" cy="7" r="4"/><path d="M20 8v6M23 11h-6"/>
                </svg>
            </div>
            <div class="number">${totalHospedes}</div>
            <div class="label">Hóspedes cadastrados</div>
            <a href="${pageContext.request.contextPath}/hospedes" class="btn btn-primary">Gerenciar</a>
        </div>
        <div class="stat-card">
            <div class="icone-topo">
                <svg class="icone icone-grande" viewBox="0 0 24 24" fill="none" stroke="#245580" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M3 11h18"/><path d="M5 11V7a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v4"/><path d="M3 11v8M21 11v8M3 17h18"/>
                </svg>
            </div>
            <div class="number">${totalQuartos}</div>
            <div class="label">Quartos disponíveis</div>
            <a href="${pageContext.request.contextPath}/quartos" class="btn btn-primary">Gerenciar</a>
        </div>
        <div class="stat-card">
            <div class="icone-topo">
                <svg class="icone icone-grande" viewBox="0 0 24 24" fill="none" stroke="#245580" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/><path d="M9 16l2 2 4-4"/>
                </svg>
            </div>
            <div class="number">${totalReservas}</div>
            <div class="label">Reservas registradas</div>
            <a href="${pageContext.request.contextPath}/reservas" class="btn btn-primary">Gerenciar</a>
        </div>
        <div class="stat-card">
            <div class="icone-topo">
                <svg class="icone icone-grande" viewBox="0 0 24 24" fill="none" stroke="#245580" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M9 11l3 3L22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/>
                </svg>
            </div>
            <div class="number">&nbsp;</div>
            <div class="label">Check-ins</div>
            <a href="${pageContext.request.contextPath}/checkins" class="btn btn-primary">Gerenciar</a>
        </div>
    </div>
</div>

<footer>Sistema <b>HOSPEDAR</b> · Aplicações para Internet</footer>
</body>
</html>
