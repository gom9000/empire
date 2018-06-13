<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="net.gos95.empire.waf.util.navigator.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>EWA.alpha - login page</title>
  </head>

  <body>
    <form name="login" method="POST" action="<%= getServletContext().getInitParameter("dispatcherURL") %>">
      <input type="hidden" name="<%= Navigator.PARAMETER %>" value="login">

      <h1>Login Page</h1>

      Username: <input type="text" name="username">
      <br>
      Password: <input type="password" name="password">
      <br>
      <input type="submit" name="Ok">
    </form>
  </body>
</html>
