<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>${empty quarto ? 'Novo Quarto' : 'Editar Quarto'} - Hotel</title>
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
    <div class="card" style="max-width:550px; margin:0 auto;">
        <div class="card-header">
            <h2>${empty quarto ? '+ Novo Quarto' : '✏️ Editar Quarto'}</h2>
        </div>
        <div class="card-body">
            <c:if test="${not empty erro}"><div class="alert alert-danger">${erro}</div></c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/quartos/${empty quarto ? 'salvar' : 'atualizar'}">
                <c:if test="${not empty quarto}">
                    <input type="hidden" name="id" value="${quarto.id}">
                </c:if>

                <div class="form-row">
                    <div class="form-group">
                        <label>Número do Quarto *</label>
                        <input type="text" name="numero" value="${quarto.numero}" placeholder="Ex: 101" required>
                    </div>
                    <div class="form-group">
                        <label>Tipo *</label>
                        <select name="tipo" required>
                            <option value="">Selecione...</option>
                            <option value="SIMPLES" ${quarto.tipo == 'SIMPLES' ? 'selected' : ''}>Simples</option>
                            <option value="DUPLO" ${quarto.tipo == 'DUPLO' ? 'selected' : ''}>Duplo</option>
                            <option value="SUITE" ${quarto.tipo == 'SUITE' ? 'selected' : ''}>Suíte</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label>Preço da Diária (R$) *</label>
                    <input type="number" step="0.01" name="precoDiaria" value="${quarto.precoDiaria}" required>
                </div>

                <div class="form-group">
                    <label>Descrição</label>
                    <textarea name="descricao" rows="3">${quarto.descricao}</textarea>
                </div>

                <div class="form-group">
                    <label>
                        <input type="checkbox" name="disponivel" ${empty quarto || quarto.disponivel ? 'checked' : ''}>
                        Quarto disponível
                    </label>
                </div>

                <div style="display:flex; gap:10px;">
                    <button type="submit" class="btn btn-primary">💾 Salvar</button>
                    <a href="${pageContext.request.contextPath}/quartos" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
