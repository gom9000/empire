<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="net.gos95.empire.waf.template.alpha.*" %>
<%@ page import="net.gos95.empire.waf.util.messages.*" %>
<%@ page import="net.gos95.empire.waf.util.navigator.*" %>
<%@ page import="net.gos95.empire.util.*" %>
<%@ page import="java.util.*" %>


<%
    // retrieve session objects
    ObjectListManager sessionOLM = (ObjectListManager)request.getSession().getAttribute("sessionOLM");
    Navigator navigator = (Navigator)sessionOLM.get("navigator");
%>


<html>
  <head>
    <title>EWA.alpha - Function-one page</title>
    <script>
      function goRoute(route, step) {
          document.message.<%= Navigator.PARAMETER %>.value = route;
          document.message.<%= Navigator.STEP %>.value = step;
          document.message.submit();
      }
    </script>
  </head>

  <body>
    <form name="message" method="POST" action="<%= getServletContext().getInitParameter("dispatcherURL") %>">
      <input type="hidden" name="<%= Navigator.PARAMETER %>">
      <input type="hidden" name="<%= Navigator.STEP %>">

      <h1><%= navigator.getRoute().name %></h1>
      Current route: <pre><%= navigator.getRoute().describe() %></pre>

      <input value="Do Function One" type="button" onClick="goRoute('f1', '1');">

    </form>
  </body>
</html>
