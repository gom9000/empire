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

    ControllerException exception = (ControllerException)request.getAttribute("exception");
    MessageBox mbox = exception.getMessageBox();

    String type = null;
    int itype = -1;
    if (mbox != null) itype = mbox.getType();

    switch (itype)
    {
    case Message.INFORMATION:
        type = "INFORMATION";
        break;
    case Message.QUESTION:
        type = "QUESTION";
        break;
    case Message.WARNING:
        type = "WARNING";
        break;
    case Message.ERROR:
        type = "ERROR";
        break;
    default:
        type = "UNKNOWN";
        break;
    }
%>

<html>
  <head>
    <title>EWA.alpha - message page</title>
    <script>
      function doAction(action) {
          document.message.<%= Navigator.PARAMETER %>.value = action;
          document.message.submit();
      }
    </script>
  </head>

  <body>
    <form name="message" method="POST" action="<%= getServletContext().getInitParameter("dispatcherURL") %>">
      <input type="hidden" name="<%= Navigator.PARAMETER %>">

      <h1>Message Page</h1>

      message type: <pre><%= type %></pre>

      message value: <pre><%= mbox.getValue() %></pre>

      exception string: <pre><%= exception.toString() %></pre>

      exception stack: <pre><%= exception.toStackString() %></pre>

      message actions:
    
      <% String label = "", action = ""; %>
      <% if (mbox.getActionEnum().hasMoreElements()) { %>
      <%     for (Enumeration e=mbox.getActionEnum(); e.hasMoreElements();) { %>
      <%         label  = (String)e.nextElement(); %>
      <%         action = mbox.getAction(label);   %>
                 <input value="<%= label %>" type="button" onClick="doAction('<%= action %>');">
      <%     } %>
      <% } %>

    </form>
  </body>
</html>
