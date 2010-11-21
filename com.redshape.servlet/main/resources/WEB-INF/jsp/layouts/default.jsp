<%@ page import="com.redshape.servlet.views.View" %>
<%
    String viewPath = ( (com.redshape.servlet.views.View) session.getAttribute("view") ).getScriptPath();
%>
<html>
    <head>
        <title>Make It Live!</title>
    </head>
    <body>
        <div id="page">
            <jsp:include page="${viewPath}"/>
        </div>

        <div id="footer">
            (c) 2010 - <a href="http://redshape.ru"><strong>Redshape Technologies</strong></a>
        </div>
    </body>
</html>