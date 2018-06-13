<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="net.gos95.empire.waf.template.alpha.*" %>
<%@ page import="net.gos95.empire.waf.util.messages.*" %>
<%@ page import="net.gos95.empire.waf.util.navigator.*" %>
<%@ page import="net.gos95.empire.waf.util.session.*" %>
<%@ page import="net.gos95.empire.util.*" %>
<%@ page import="java.util.*" %>


<%
    // retrieve session objects
    ObjectListManager applicationOLM = (ObjectListManager)getServletContext().getAttribute("applicationOLM");
    ObjectListManager sessionOLM = (ObjectListManager)request.getSession().getAttribute("sessionOLM");
    Navigator navigator = (Navigator)sessionOLM.get("navigator");
    SessionMonitor monitor = (SessionMonitor)applicationOLM.get("monitor");
%>


<html>
  <head>
    <title>EWA.alpha - home page</title>
    <script>
      function goRoute(route) {
          document.message.<%= Navigator.PARAMETER %>.value = route;
          document.message.submit();
      }
    </script>
  </head>

  <body>
    <form name="message" method="POST" action="<%= getServletContext().getInitParameter(Constants.DISPATCHER_URL) %>">
      <input type="hidden" name="<%= Navigator.PARAMETER %>">

      <h1><%= navigator.getRoute().name %></h1>
      Current route: <pre><%= navigator.getRoute().describe() %></pre>

      Welcome <%= monitor.getUsername(session) %>
      <br>

      Your routes:
      <br>
          <% Collection c = navigator.getRouteChildrenCodes(null); %>
          <% Iterator iterator = c.iterator(); %>
          <% while (iterator.hasNext()) { %>
          <%     Route r = navigator.getRoute((String)iterator.next()); %>
          <%     if (r.enable == true && r.menuitem != null) { %>
                     <input value="<%= r.menuitem %>" type="button" onClick="goRoute('<%= r.code %>');">
          <%     } %>
          <% } %>
      <br><br>

      Session Monitor:
	  <% c = monitor.values(); %>
      <% iterator = c.iterator(); %>
	  <% while (iterator.hasNext()) { %>
	  <%     SessionData d = (SessionData)iterator.next(); %>
	         <pre><%= d.toString() + "<br>" + d.describe() %></pre>
	  <% } %>

    </form>
  </body>
</html>
