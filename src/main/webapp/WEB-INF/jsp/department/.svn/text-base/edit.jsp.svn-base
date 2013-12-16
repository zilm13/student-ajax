<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="department.edit.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>" />
</head>
<body>
    <div id="content">
        <h1><fmt:message key="department.edit.heading"/></h1>
        <c:if test="${empty departmentError}">
        <form:form id="registration" method="post" commandName="departmentForm" enctype="UTF-8" acceptCharset="UTF-8">
        <ul>
            <li>
                <label for="departmentName"><fmt:message key="department.edit.form.departmentname"/></label>
                <form:input path="departmentName"/>
                <p>&nbsp;<form:errors path="departmentName"/></p>
            </li>
            <li>
                <label for="deanFirstName"><fmt:message key="department.edit.form.deanfirstname"/></label>
                <form:input path="deanFirstName"/>
                <p>&nbsp;<form:errors path="deanFirstName"/></p>
            </li>
            <li>
                <label for="deanLastName"><fmt:message key="department.edit.form.deanlastname"/></label>
                <form:input path="deanLastName"/>
                <p>&nbsp;<form:errors path="deanLastName"/></p>
            </li>
            <li class="center">
                <form:input type="hidden" path="id"/>
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='department.edit.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='department.edit.form.ok'/>" />
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