<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Quartos - Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/img/logo-nav.png" alt="HOSPEDAR"><span class="brand-text"><b>HOSPEDAR</b> Quick Check-in</span></a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/hospedes">Hóspedes</a></li>
        <li><a href="${pageContext.request.contextPath}/quartos" class="active">Quartos</a></li>
        <li><a href="${pageContext.request.contextPath}/reservas">Reservas</a></li>
        <li><a href="${pageContext.request.contextPath}/checkins">Check-ins</a></li>
    </ul>
</nav>

<div class="container">
    <c:if test="${param.msg == 'cadastrado'}"><div class="alert alert-success">Quarto cadastrado!</div></c:if>
    <c:if test="${param.msg == 'atualizado'}"><div class="alert alert-success">Quarto atualizado!</div></c:if>
    <c:if test="${param.msg == 'excluido'}"><div class="alert alert-success">Quarto excluído!</div></c:if>
    <c:if test="${not empty erro}"><div class="alert alert-danger">${erro}</div></c:if>

    <div class="card">
        <div class="card-header">
            <h2><svg class="icone" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M3 11h18"/><path d="M5 11V7a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v4"/><path d="M3 11v8M21 11v8M3 17h18"/></svg> Quartos</h2>
            <a href="${pageContext.request.contextPath}/quartos/novo" class="btn btn-warning">+ Novo Quarto</a>
        </div>
        <div class="card-body">
            <table>
                <thead>
                <tr>
                    <th>Número</th>
                    <th>Tipo</th>
                    <th>Diária</th>
                    <th>Status</th>
                    <th>Descrição</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="q" items="${quartos}">
                    <tr>
                        <td><strong>${q.numero}</strong></td>
                        <td>${q.tipo}</td>
                        <td>R$ ${q.precoDiaria}</td>
                        <td>
                            <c:choose>
                                <c:when test="${q.disponivel}"><span class="badge badge-disponivel">Disponível</span></c:when>
                                <c:otherwise><span class="badge badge-ocupado">Ocupado</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>${q.descricao}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/quartos/editar?id=${q.id}" class="btn btn-warning btn-sm">Editar</a>
                            <a href="${pageContext.request.contextPath}/quartos/excluir?id=${q.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Excluir quarto ${q.numero}?')">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
