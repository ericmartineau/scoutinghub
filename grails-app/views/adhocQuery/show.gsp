<%--
  Created by IntelliJ IDEA.
  User: sparhk
  Date: 12/14/12
  Time: 5:44 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Simple GSP page</title></head>
  <body>
  <g:form action="runQuery">
            <s:textField code="SQL" name="sqlQuery"/>
            <div class="buttons">
                <span class="button"><g:submitButton name="create" class="results" value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
            </div>
        </g:form>

  </body>
</html>