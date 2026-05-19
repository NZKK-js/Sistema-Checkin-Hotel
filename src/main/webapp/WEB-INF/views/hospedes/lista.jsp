<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Hóspedes - Hotel</title>
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
    <c:if test="${param.msg == 'cadastrado'}">
        <div class="alert alert-success">Hóspede cadastrado com sucesso!</div>
    </c:if>
    <c:if test="${param.msg == 'atualizado'}">
        <div class="alert alert-success">Hóspede atualizado com sucesso!</div>
    </c:if>
    <c:if test="${param.msg == 'excluido'}">
        <div class="alert alert-success">Hóspede excluído com sucesso!</div>
    </c:if>
    <c:if test="${not empty erro}">
        <div class="alert alert-danger">${erro}</div>
    </c:if>

    <div class="card">
        <div class="card-header">
            <h2><svg class="icone" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M16 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="10" cy="7" r="4"/><path d="M20 8v6M23 11h-6"/></svg> Hóspedes</h2>
            <a href="${pageContext.request.contextPath}/hospedes/novo" class="btn btn-warning">+ Novo Hóspede</a>
        </div>
        <div class="card-body">
            <form method="get" action="${pageContext.request.contextPath}/hospedes" class="search-bar">
                <input type="text" name="busca" placeholder="Buscar por nome..." value="${busca}">
                <button type="submit" class="btn btn-primary">Buscar</button>
                <a href="${pageContext.request.contextPath}/hospedes" class="btn btn-secondary">Limpar</a>
            </form>

            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>E-mail</th>
                    <th>Telefone</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="h" items="${hospedes}">
                    <tr>
                        <td>${h.id}</td>
                        <td><strong>${h.nome}</strong></td>
                        <td>${h.cpf}</td>
                        <td>${h.email}</td>
                        <td>${h.telefone}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/hospedes/editar?id=${h.id}" class="btn btn-warning btn-sm">Editar</a>
                            <a href="${pageContext.request.contextPath}/hospedes/excluir?id=${h.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Deseja excluir este hóspede?')">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty hospedes}">
                    <tr><td colspan="6" style="text-align:center; color:#999; padding:30px;">Nenhum hóspede encontrado.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<footer>Sistema de Check-in de Hotel</footer>
</body>
</html>
