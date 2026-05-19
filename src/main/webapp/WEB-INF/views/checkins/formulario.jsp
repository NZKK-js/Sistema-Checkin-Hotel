<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Novo Check-in - Hotel</title>
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
    <div class="card" style="max-width:560px; margin:0 auto;">
        <div class="card-header">
            <h2><svg class="icone" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M9 11l3 3L22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg> Realizar Check-in</h2>
        </div>
        <div class="card-body">
            <c:if test="${not empty erro}"><div class="alert alert-danger">${erro}</div></c:if>
            <c:if test="${empty reservas}">
                <div class="alert alert-info">
                    Não há reservas confirmadas disponíveis para check-in.
                    <a href="${pageContext.request.contextPath}/reservas">Ver reservas</a>
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/checkins/salvar">
                <div class="form-group">
                    <label>Reserva Confirmada *</label>
                    <select name="reservaId" required>
                        <option value="">Selecione a reserva...</option>
                        <c:forEach var="r" items="${reservas}">
                            <option value="${r.id}">
                                #${r.id} - ${r.hospede.nome} | Quarto ${r.quarto.numero} | ${r.dataEntrada} → ${r.dataSaida}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Observações</label>
                    <textarea name="observacoes" rows="3" placeholder="Pedidos especiais, observações do hóspede..."></textarea>
                </div>

                <div style="display:flex; gap:10px;">
                    <button type="submit" class="btn btn-primary"><svg class="icone" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M20 6L9 17l-5-5"/></svg> Confirmar Check-in</button>
                    <a href="${pageContext.request.contextPath}/checkins" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
