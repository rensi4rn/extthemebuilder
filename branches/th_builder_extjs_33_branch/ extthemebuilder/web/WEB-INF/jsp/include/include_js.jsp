<%@ page import="cesium.holder.CSSHolder"%><%@ page import="org.apache.commons.beanutils.PropertyUtils"%><%@ page import="org.springframework.context.support.MessageSourceAccessor"%><%@ page import="springapp.constants.ApplicationConstants"%><%@ page import="javax.servlet.jsp.jstl.core.LoopTagStatus"%><%@ page import="java.lang.reflect.InvocationTargetException"%><%@ page import="java.util.*"%>

<%@ page contentType="text/javascript;charset=UTF-8" language="java" %>
<%@ page buffer="none" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>

