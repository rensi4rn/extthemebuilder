<%@ page contentType="text/html;charset=UTF-8" language="java" %><%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
    String cb=request.getParameter("callback");
%><%= (null!=cb?cb+"(":"")+"{\"success\":\"true\"}"+(null!=cb?")":"")%>

