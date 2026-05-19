<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>${empty hospede ? 'Novo Hóspede' : 'Editar Hóspede'} - Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/img/logo-nav.png" alt="HOSPEDAR"><span class="brand-text"><b>HOSPEDAR</b> Quick Check-in</span></a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/hospedes" class="active">Hóspedes</a></li>
        <li><a href="${pageContext.request.contextPath}/quartos">Quartos</a></li>
        <li><a href="${pageContext.request.contextPath}/reservas">Reservas</a></li>
        <li><a href="${pageContext.request.contextPath}/checkins">Check-ins</a></li>
    </ul>
</nav>

<div class="container">
    <div class="card" style="max-width:600px; margin:0 auto;">
        <div class="card-header">
            <h2>${empty hospede ? '+ Novo Hóspede' : '✏️ Editar Hóspede'}</h2>
        </div>
        <div class="card-body">
            <c:if test="${not empty erro}">
                <div class="alert alert-danger">${erro}</div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/hospedes/${empty hospede ? 'salvar' : 'atualizar'}">
                <c:if test="${not empty hospede}">
                    <input type="hidden" name="id" value="${hospede.id}">
                </c:if>

                <div class="form-group">
                    <label>Nome completo *</label>
                    <input type="text" name="nome" value="${hospede.nome}" required>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>CPF *</label>
                        <input type="text" name="cpf" maxlength="14" placeholder="000.000.000-00" value="${hospede.cpf}" placeholder="000.000.000-00" required>
                    </div>
                    <div class="form-group">
                        <label>Telefone</label>
                        <input type="text" name="telefone" value="${hospede.telefone}" placeholder="(00) 99999-9999">
                    </div>
                </div>

                <div class="form-group">
                    <label>E-mail *</label>
                    <input type="email" name="email" value="${hospede.email}" required>
                </div>

                <div class="form-group">
                    <label>Data de Nascimento</label>
                    <input type="date" name="dataNascimento" value="${hospede.dataNascimento}">
                </div>

                <div style="display:flex; gap:10px; margin-top:10px;">
                    <button type="submit" class="btn btn-primary">💾 Salvar</button>
                    <a href="${pageContext.request.contextPath}/hospedes" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const cpfInput = document.querySelector('input[name="cpf"]');

    if (cpfInput) {
        cpfInput.addEventListener("input", function () {
            let valor = cpfInput.value.replace(/\D/g, "");

            valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
            valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
            valor = valor.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

            cpfInput.value = valor;
        });
    }
});
</script>
</body>

</html>
