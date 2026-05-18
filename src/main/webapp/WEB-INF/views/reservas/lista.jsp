<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Reservas - Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><span>🏨</span> HotelSystem</a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/hospedes">Hóspedes</a></li>
        <li><a href="${pageContext.request.contextPath}/quartos">Quartos</a></li>
        <li><a href="${pageContext.request.contextPath}/reservas" class="active">Reservas</a></li>
        <li><a href="${pageContext.request.contextPath}/checkins">Check-ins</a></li>
    </ul>
</nav>

<div class="container">
    <c:if test="${param.msg == 'criada'}"><div class="alert alert-success">Reserva criada com sucesso!</div></c:if>
    <c:if test="${param.msg == 'confirmada'}"><div class="alert alert-success">Reserva confirmada!</div></c:if>
    <c:if test="${param.msg == 'cancelada'}"><div class="alert alert-success">Reserva cancelada.</div></c:if>
    <c:if test="${not empty erro}"><div class="alert alert-danger">${erro}</div></c:if>

    <div class="card">
        <div class="card-header">
            <h2>📅 Reservas</h2>
            <a href="${pageContext.request.contextPath}/reservas/nova" class="btn btn-warning">+ Nova Reserva</a>
        </div>
        <div class="card-body">
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Hóspede</th>
                    <th>Quarto</th>
                    <th>Entrada</th>
                    <th>Saída</th>
                    <th>Diárias</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="r" items="${reservas}">
                    <tr>
                        <td>${r.id}</td>
                        <td><strong>${r.hospede.nome}</strong><br><small>${r.hospede.cpf}</small></td>
                        <td>Nº ${r.quarto.numero} (${r.quarto.tipo})</td>
                        <td>${r.dataEntrada}</td>
                        <td>${r.dataSaida}</td>
                        <td>${r.diarias} dia(s)</td>
                        <td>
                            <c:choose>
                                <c:when test="${r.status == 'PENDENTE'}"><span class="badge badge-pendente">Pendente</span></c:when>
                                <c:when test="${r.status == 'CONFIRMADA'}"><span class="badge badge-confirmada">Confirmada</span></c:when>
                                <c:otherwise><span class="badge badge-cancelada">Cancelada</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${r.status == 'PENDENTE'}">
                                <a href="${pageContext.request.contextPath}/reservas/confirmar?id=${r.id}" class="btn btn-success btn-sm">Confirmar</a>
                                <a href="${pageContext.request.contextPath}/reservas/cancelar?id=${r.id}" class="btn btn-danger btn-sm" onclick="return confirm('Cancelar esta reserva?')">Cancelar</a>
                            </c:if>
                            <c:if test="${r.status == 'CONFIRMADA'}">
                                <a href="${pageContext.request.contextPath}/checkins/novo" class="btn btn-primary btn-sm">Check-in</a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/reservas/excluir?id=${r.id}" class="btn btn-secondary btn-sm" onclick="return confirm('Excluir reserva?')">✕</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty reservas}">
                    <tr><td colspan="8" style="text-align:center; color:#999; padding:30px;">Nenhuma reserva encontrada.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
