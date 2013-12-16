<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="student.delete.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>">
</head>
<body>
    <div id="content">
       <h1><fmt:message key="student.delete.heading"/></h1>
       <c:if test="${empty studentError}">
       <form:form id="registration" method="post" action="" commandName="studentForm">
        <ul>
            <li>
                <fmt:message key="student.delete.text">
                    <fmt:param value="${studentForm.firstName}"/>
                    <fmt:param value="${studentForm.lastName}"/>
                    <fmt:param value="${studentForm.group.groupName}"/>
                </fmt:message>
                 <p>&nbsp;</p>
            </li>
            <li class="center">
                <form:input path="id" type="hidden" />
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='student.delete.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='student.delete.form.ok'/>" />
                <p>&nbsp;<form:errors path="id"/></p>
            </li>
        </ul>
        </form:form>
        </c:if>
        <c:if test="${not empty studentError}">
            <div class="error"><c:out value="${studentError}" /></div>
        </c:if>
    </div>
</body>
</html>