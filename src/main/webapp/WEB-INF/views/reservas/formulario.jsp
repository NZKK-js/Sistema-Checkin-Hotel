<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Nova Reserva - Hotel</title>
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
    <div class="card" style="max-width:600px; margin:0 auto;">
        <div class="card-header">
            <h2>${empty reserva ? '+ Nova Reserva' : '✏️ Editar Reserva'}</h2>
        </div>
        <div class="card-body">
            <c:if test="${not empty erro}"><div class="alert alert-danger">${erro}</div></c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/reservas/${empty reserva ? 'salvar' : 'atualizar'}">
                <c:if test="${not empty reserva}">
                    <input type="hidden" name="id" value="${reserva.id}">
                </c:if>

                <div class="form-group">
                    <label>Hóspede *</label>
                    <select name="hospedeId" required>
                        <option value="">Selecione o hóspede...</option>
                        <c:forEach var="h" items="${hospedes}">
                            <option value="${h.id}" ${reserva.hospedeId == h.id ? 'selected' : ''}>${h.nome} (${h.cpf})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Quarto *</label>
                    <select name="quartoId" required>
                        <option value="">Selecione o quarto...</option>
                        <c:forEach var="q" items="${quartos}">
                            <option value="${q.id}" ${reserva.quartoId == q.id ? 'selected' : ''}>
                                Nº ${q.numero} - ${q.tipo} - R$ ${q.precoDiaria}/diária
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Data de Entrada *</label>
                        <input type="date" name="dataEntrada" value="${reserva.dataEntrada}" required>
                    </div>
                    <div class="form-group">
                        <label>Data de Saída *</label>
                        <input type="date" name="dataSaida" value="${reserva.dataSaida}" required>
                    </div>
                </div>

                <div style="display:flex; gap:10px; margin-top:10px;">
                    <button type="submit" class="btn btn-primary">💾 Salvar Reserva</button>
                    <a href="${pageContext.request.contextPath}/reservas" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
