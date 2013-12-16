<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="department.delete.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>">
</head>
<body>
    <div id="content">
        <h1><fmt:message key="department.delete.heading"/></h1>
        <c:if test="${empty departmentError}">
        <form:form id="registration" method="post" action="" commandName="departmentForm">
        <ul>
            <li>
                <fmt:message key="department.delete.text">
                    <fmt:param value="${departmentForm.departmentName}"/>
                    <fmt:param value="${departmentForm.deanFirstName}"/>
                    <fmt:param value="${departmentForm.deanLastName}"/>
                </fmt:message>
                 <p>&nbsp;</p>
            </li>
            <li class="center">
                <form:input path="id" type="hidden" />
                <form:input path="departmentName" type="hidden" />
                <form:input path="deanFirstName" type="hidden" />
                <form:input path="deanLastName" type="hidden" />
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='department.delete.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='department.delete.form.ok'/>" />
                <p>&nbsp;<form:errors path="id"/></p>
            </li>
        </ul>
        </form:form>
        </c:if>
        <c:if test="${not empty departmentError}">
        <div class="error"><c:out value="${departmentError}" /></div>
        </c:if>
    </div>
</body>
</html>