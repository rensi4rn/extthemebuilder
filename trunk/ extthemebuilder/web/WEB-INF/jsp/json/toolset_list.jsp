<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
{images:[
    {name:'ExtJS',size:100,lastmod:'0',url:'images/toolsets/default.jpg',value:'default'},
    {name:'Vista',size:100,lastmod:'0',url:'images/toolsets/vista.jpg',value:'vista'},
    {name:'TargetProcess',size:100,lastmod:'0',url:'images/toolsets/tp.jpg',value:'tp'},
    {name:'Graphite',size:100,lastmod:'0',url:'images/toolsets/graphite.jpg',value:'graphite'}
]}