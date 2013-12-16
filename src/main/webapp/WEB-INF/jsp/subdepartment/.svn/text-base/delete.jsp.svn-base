<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="subdepartment.delete.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>">
</head>
<body>
    <div id="content">
        <h1><fmt:message key="subdepartment.delete.heading"/></h1>
        <c:if test="${empty subDepartmentError}">
        <form:form id="registration" method="post" action="" commandName="subDepartmentForm">
        <ul>
            <li>
                <fmt:message key="subdepartment.delete.text">
                    <fmt:param value="${subDepartmentForm.subDepartmentName}"/>
                    <fmt:param value="${subDepartmentForm.department.departmentName}"/>
                </fmt:message>
                 <p>&nbsp;</p>
            </li>
            <li class="center">
                <form:input path="id" type="hidden" />
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='subdepartment.delete.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='subdepartment.delete.form.ok'/>" />
                <p>&nbsp;<form:errors path="id"/></p>
            </li>
        </ul>
        </form:form>
        </c:if>
        <c:if test="${not empty subDepartmentError}">
        <div class="error"><c:out value="${subDepartmentError}" /></div>
        </c:if>
    </div>
</body>
</html>