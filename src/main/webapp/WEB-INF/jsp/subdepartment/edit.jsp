<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="subdepartment.edit.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>" />
</head>
<body>
    <div id="content">
        <h1><fmt:message key="subdepartment.edit.heading"/></h1>
        <c:if test="${empty subDepartmentError}">
        <form:form id="registration" method="post" commandName="subDepartmentForm">
        <ul>
            <li>
                <label for="subDepartmentName"><fmt:message key="subdepartment.edit.form.subdepartmentname"/></label>
                <form:input path="subDepartmentName"/>
                <p>&nbsp;<form:errors path="subDepartmentName"/></p>
            </li>
            <li>
                <label for="department"><fmt:message key="subdepartment.edit.form.department"/></label>
                <form:select path="department" >
                    <option value=""><fmt:message key="subdepartment.edit.form.department.choose"/></option>
                  <c:forEach items="${departments}" var="department">
                      <option value="${department.id}" <c:if test="${subDepartmentForm.department.id == department.id}">selected</c:if> ><c:out value="${department.departmentName}"/></option>
                  </c:forEach>
                </form:select>
                <p>&nbsp;<form:errors path="department"/></p>
            </li>
            <li class="center">
                <form:input type="hidden" path="id"/>
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='subdepartment.edit.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='subdepartment.edit.form.ok'/>" />
                <p>&nbsp;</p>
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