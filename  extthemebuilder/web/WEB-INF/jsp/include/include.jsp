<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
<link rel="shortcut icon" href="<c:url value="/images/themes.ico"/>" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="<c:url value="/js/ext/resources/css/ext-all.css"/>" />
<link rel="stylesheet" type="text/css" id="theme" href="" />

<script type="text/javascript" src="<c:url value="/js/ext/adapter/ext/ext-base.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ext/ext-all.js"/>"></script>

<script type="text/javascript" >
    Ext.BLANK_IMAGE_URL = '<c:url value="/js/ext/resources/images/default/s.gif"/>';
</script>
