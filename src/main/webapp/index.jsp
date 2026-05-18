<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Página de entrada: encaminha para o HomeController (/home) --%>
<% response.sendRedirect(request.getContextPath() + "/home"); %>
