<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Check-ins - Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/img/logo-nav.png" alt="HOSPEDAR"><span class="brand-text"><b>HOSPEDAR</b> Quick Check-in</span></a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/hospedes">Hóspedes</a></li>
        <li><a href="${pageContext.request.contextPath}/quartos">Quartos</a></li>
        <li><a href="${pageContext.request.contextPath}/reservas">Reservas</a></li>
        <li><a href="${pageContext.request.contextPath}/checkins" class="active">Check-ins</a></li>
    </ul>
</nav>

<div class="container">
    <c:if test="${param.msg == 'checkin'}"><div class="alert alert-success"><svg class="icone" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M20 6L9 17l-5-5"/></svg> Check-in realizado com sucesso!</div></c:if>
    <c:if test="${param.msg == 'checkout'}"><div class="alert alert-success">🚪 Checkout realizado. Quarto liberado!</div></c:if>
    <c:if test="${param.msg == 'cancelado'}"><div class="alert alert-success">Check-in cancelado.</div></c:if>
    <c:if test="${not empty erro}"><div class="alert alert-danger">${erro}</div></c:if>

    <div class="card">
        <div class="card-header">
            <h2><svg class="icone" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M9 11l3 3L22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg> Check-ins</h2>
            <a href="${pageContext.request.contextPath}/checkins/novo" class="btn btn-warning">+ Novo Check-in</a>
        </div>
        <div class="card-body">
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Hóspede</th>
                    <th>Quarto</th>
                    <th>Entrada Prevista</th>
                    <th>Saída Prevista</th>
                    <th>Check-in em</th>
                    <th>Checkout em</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="c" items="${checkins}">
                    <tr>
                        <td>${c.id}</td>
                        <td><strong>${c.reserva.hospede.nome}</strong></td>
                        <td>Nº ${c.reserva.quarto.numero} (${c.reserva.quarto.tipo})</td>
                        <td>${c.reserva.dataEntrada}</td>
                        <td>${c.reserva.dataSaida}</td>
                        <td>${c.dataCheckin}</td>
                        <td>${empty c.dataCheckout ? '-' : c.dataCheckout}</td>
                        <td>
                            <c:choose>
                                <c:when test="${c.status == 'ATIVO'}"><span class="badge badge-ativo">Ativo</span></c:when>
                                <c:otherwise><span class="badge badge-finalizado">Finalizado</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${c.status == 'ATIVO'}">
                                <a href="${pageContext.request.contextPath}/checkins/checkout?id=${c.id}"
                                   class="btn btn-primary btn-sm"
                                   onclick="return confirm('Confirmar checkout?')">🚪 Checkout</a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/checkins/cancelar?id=${c.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Cancelar este check-in?')">✕</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty checkins}">
                    <tr><td colspan="9" style="text-align:center; color:#999; padding:30px;">Nenhum check-in registrado.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
