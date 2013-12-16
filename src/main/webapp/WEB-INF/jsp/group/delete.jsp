<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="group.delete.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>">
</head>
<body>
    <div id="content">
        <h1><fmt:message key="group.delete.heading"/></h1>
        <c:if test="${empty groupError}">
        <form:form id="registration" method="post" action="" commandName="groupForm">
        <ul>
            <li>
                <fmt:message key="group.delete.text">
                    <fmt:param value="${groupForm.groupName}"/>
                    <fmt:param value="${groupForm.department.departmentName}"/>
                </fmt:message>
                 <p>&nbsp;</p>
            </li>
            <li class="center">
                <form:input path="id" type="hidden" />
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='group.delete.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='group.delete.form.ok'/>" />
                <p>&nbsp;<form:errors path="id"/></p>
            </li>
        </ul>
        </form:form>
        </c:if>
        <c:if test="${not empty groupError}">
        <div class="error"><c:out value="${groupError}" /></div>
        </c:if>
    </div>
</body>
</html>